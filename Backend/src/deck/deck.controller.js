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
        const deckName = req.params.deckName;
        const result = await deckService.getDeckByName(deckName);
        res.status(200).json({
            success:true,
            message: "Deck cards sent",
            payload: result
        });
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

async function getDeckById(req, res){
    try{
        const deckId = req.params.deckId;
        //console.log(deckId);
        const result = await deckService.getDeckById(deckId);
        if(result.cardId ==1){
            return res.status(401).json({
                success:false,
                message: result.message,
                payload: result
            });
        }
        res.status(200).json({
            success:true,
            message: "Deck cards sent",
            payload: result
        });
    }catch(error){
        res.status(500).json(error);
    }
}

async function deleteDeck(req, res){
    try{
        //console.log(deckId);
        const result = await deckService.deleteDeck(req.params.userId, req.params.deckId);
        if(result.success ===false){
            return res.status(401).json({
                success:false,
                message: result.message,
                payload: result
            });
        }
        res.status(200).json({
            success:true,
            message: "Deck has been deleted",
            payload: result
        });
    }catch(error){
        console.log(error);
        res.status(500).json(error);
    }
}

module.exports = {
    createDeck,
    getAllDecks,
    getDeckByName,
    updateDeckCards,
    getDeckById,
    deleteDeck
};