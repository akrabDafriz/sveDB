const pool = require('../db');

async function createDeck(deckDetails){
    const { deckName, userId, cards } = deckDetails;

    //check if total < 40
    // const totalCards = cards.reduce((sum, card) => sum + card.quantity, 0);
    // if (totalCards < 40) {
    //     return res.status(400).json({ error: 'A deck must contain at least 40 cards.' });
    // }

    // Check if same name >3
    // const cardCounts = {};
    // for (const card of cards) {
    //     cardCounts[card.name] = (cardCounts[card.name] || 0) + card.quantity;
    //     if (cardCounts[card.name] > 3) {
    //         return res.status(400).json({ error: `A deck can only contain up to 3 cards with the same name: ${card.name}.` });
    //     }
    // }

    const deckResult = await pool.query(
        'INSERT INTO deck_list (user_id, deck_name) VALUES ($1, $2) RETURNING id',
        [userId, deckName]
    );
    const deckId = deckResult.rows[0].id;

    for (const card of cards) {
        const cardResult = await pool.query(
            'SELECT id FROM card_database WHERE card_name = $1',
            [card.name]
        );

        if (cardResult.rows.length === 0) {
            throw new Error(`Card with name ${card.name} does not exist.`);
        }

        const cardId = cardResult.rows[0].id;
        
        await pool.query(
            'INSERT INTO deck_cards (deck_id, card_id, amount) VALUES ($1, $2, $3)',
            [deckId, cardId, card.quantity]
        );
    }

    return deckId;
}

async function getAllDecks(){
    const decks = await pool.query('SELECT * FROM deck_list');
    console.log(decks.rows);
    return decks.rows;
}

async function getDeckByName(deckName){
    const queryText = `SELECT dc.deck_id, cd.*, dc.amount
    FROM deck_list dl 
    JOIN deck_cards dc ON dl.id = dc.deck_id 
    JOIN card_database cd ON dc.card_id = cd.id 
    WHERE dl.deck_name = $1;`
    const result = await pool.query(queryText, [deckName]);

    if (result.rows.length === 0) {
        const deck = {
            deckId: -1,
            cards: {
                message: "Not Found",
                cardId: -1
            },
            amount: 0
        }
        return deck;
        //throw new Error('Deck is not found or empty');
    }

    const deck = result.rows;

    return deck;
}

async function getDeckById(deckId){
    const queryText = `SELECT dc.deck_id, dl.deck_name, cd.*, dc.amount
    FROM deck_list dl 
    JOIN deck_cards dc ON dl.id = dc.deck_id 
    JOIN card_database cd ON dc.card_id = cd.id 
    WHERE dl.id = $1;`
    const result = await pool.query(queryText, [deckId]);

    if (result.rows.length === 0) {
        const deck = {
            deckId: -1,
            cards: {
                message: "Not Found",
                cardId: -1
            },
            amount: 0
        }
        return deck;
        //throw new Error('Deck is not found or empty');
    }

    return result.rows;
}

async function updateDeckCards(deckDetails){
    const { deckId, userId, deckName, cards } = deckDetails;
    //{ deckName, userId, cards }
    
    const result = await getDeckById(deckId);

    if (result.rows.length === 0){
        const deck = {
            deckId: -1,
            deckName:"Deck Not Found",
            cards: {
                message: "Deck Not Found",
                deckId: -1
            },
            amount: 0
        }
        return deck;
    }

    pool.query(
        `UPDATE deck_list
        SET deck_name = $1
        WHERE id = $2`, 
        [deckName, deckId]
    );

    for (const card of cards) {
        const cardResult = await pool.query(
            'SELECT id FROM card_database WHERE card_name = $1 RETURNING id',
            [card.name]
        );

        if (cardResult.rows.length === 0) {
            throw new Error(`Card with name ${card.name} does not exist.`);
        }

        const cardId = cardResult.rows[0].id;

        const cardExistsInDeck = await pool.query(
            'SELECT card_name FROM deck_cards WHERE card_id = $1',
            [cardId]
        )

        if (cardExistsInDeck.rows.length === 0){
            await pool.query(
                'INSERT INTO deck_cards (deck_id, card_id, amount) VALUES ($1, $2, $3)',
                [deckId, cardId, card.quantity]
            );
        }
        else{
            await pool.query(
                `UPDATE deck_cards
                SET amount = $1
                WHERE card_id = $2`,
                [card.quantity, cardId]
            );
        }
    }



    return deckId;
}

module.exports = {
    createDeck,
    getAllDecks,
    getDeckByName,
    getDeckById,
    updateDeckCards
};