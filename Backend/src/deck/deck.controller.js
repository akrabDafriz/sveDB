const deckService = require('./deck.service.js');

async function createDeck(req, res){
    try{
        const deckId = await deckService.createDeck(req.body);
        const deck = await deckService.getDeckById(deckId);
        res.status(200).json({
            success: true,
            message: 'Deck created successfully', 
            payload: deckId});
    }catch(error){
        console.log(error);
        res.status(500).json({error});
    }
}

async function getAllDecks(req, res){
    try{
        const result = await deckService.getAllDecks();
        const response = {
            success: true,
            message: "All decks sent",
            payload: result
        }
        res.status(200).json(response);
    }catch(error){
        res.status(500).json(error);
    }
}

async function getDeckByName(req, res){
    try{
        const { deckName } = req.query;
        const result = await deckService.getDeckByName(deckName);
        res.status(200).json(result);
    }catch(error){
        res.status(500).json(error);
    }
}

async function updateDeckCards(req, res){
    try{
        const deckId = await deckService.updateDeck(req.body);
        const deck = await deckService.getDeckById(deckId);
        res.status(200).json({
            message: 'Deck updated successfully', 
            deck: deck 
        });
    }catch(error){
        res.status(500).json({
            error: error,
            message: "Server Error"
        })
    }
}

module.exports = {
    createDeck,
    getAllDecks,
    getDeckByName,
    updateDeckCards
};