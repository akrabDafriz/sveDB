const express = require('express');
const router = express.Router();
const deckController = require('./deck.controller');

router.post('/create', deckController.createDeck);
router.get('/', deckController.getAllDecks);
router.get('/cards', deckController.getDeckByName);

module.exports = router;