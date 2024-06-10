const cardsService = require('./cards.service.js');


async function getCards(req, res) {
    try {
        const result = await cardsService.getCards();
        res.status(200).json({
            success: true,
            message: "Cards delivered",
            payload: result
        });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
}

async function getCardsFiltered(req, res) {
    try {
        const result = await cardsService.getCardsFiltered(req.body);
        res.status(200).json({
            success: true,
            message: "Cards filtered",
            payload: result
        });
    } catch (error) {
        console.error('Error retrieving cards with filter:', error.message);
        res.status(500).json({ error: 'Server Error' });
    }
}

module.exports = {
    getCards,
    getCardsFiltered
};