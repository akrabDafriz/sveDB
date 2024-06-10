const express = require('express');
const router = express.Router();
const cardsController = require('./cards.controller');

router.get('/', cardsController.getCards);
router.post('/filtered', cardsController.getCardsFiltered);

module.exports = router;