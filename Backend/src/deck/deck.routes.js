const express = require('express');
const router = express.Router();
const deckController = require('./deck.controller');

router.post('/create', deckController.createDeck);
router.get('/', deckController.getAllDecks);
router.get('/cards/:deckName', deckController.getDeckByName);
router.get('/cardsById/:deckId', deckController.getDeckById);
router.delete('/delete/:userId/:deckId', deckController.deleteDeck);

module.exports = router;