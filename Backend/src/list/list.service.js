const pool = require('../db');

async function createList(listDetails){
    const { listName, userId, cardNames, quantities} = listDetails; //req.body is passed here as listDetails
    console.log("Request Body: ");
    console.log(listDetails);

    const listResult = await pool.query(
        'INSERT INTO list_list (user_id, list_name) VALUES ($1, $2) RETURNING id',
        [userId, listName]
    );
    const listId = listResult.rows[0].id;

    let counter = 0;
    for (const cardName of cardNames) {
        const cardResult = await pool.query(
            'SELECT id FROM card_database WHERE card_name = $1',
            [cardName]
        );

        if (cardResult.rows.length === 0) {
            throw new Error(`Card with name ${card.name} does not exist.`);
        }

        const cardId = cardResult.rows[0].id;
        
        await pool.query(
            'INSERT INTO list_cards (list_id, card_id, amount) VALUES ($1, $2, $3)',
            [listId, cardId, quantities[counter]]
        );
        counter++;
    }

    return listId;
}

async function getAllLists(){
    const lists = await pool.query('SELECT * FROM list_list');
    return lists.rows;
}

async function getListByName(listName){
    const queryText = `SELECT dc.list_id, cd.*, dc.amount
    FROM list_list dl 
    JOIN list_cards dc ON dl.id = dc.list_id 
    JOIN card_database cd ON dc.card_id = cd.id 
    WHERE dl.list_name = $1;`
    const result = await pool.query(queryText, [listName]);

    if (result.rows.length === 0) {
        throw new Error('List is not found or empty');
    }

    const cardList = {
        listName: listName,
        cards: result.rows
    };

    return cardList;
}

module.exports = {
    createList,
    getAllLists,
    getListByName
};