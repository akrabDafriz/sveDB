const pool = require('../db');

async function getCards() {
    const result = await pool.query('SELECT * FROM card_database');
    //console.log(result.rows);
    return result.rows;
}

async function getCardsFiltered(cardDetails) {
    const { name, sveClass, type, cost, trait, rarity, effect, cardSet, sortBy, sortOrder } = cardDetails;
    console.log(cardDetails);
    let queryText = 'SELECT * FROM card_database WHERE 1=1';
    let queryParams = [];

    if (name) {
        queryText += ' AND card_name ILIKE $' + (queryParams.length + 1);
        queryParams.push(`%${name}%`);
    }

    if (sveClass) {
        queryText += ' AND sveClass = $' + (queryParams.length + 1);
        queryParams.push(sveClass);
    }

    if (type) {
        queryText += ' AND type = $' + (queryParams.length + 1);
        queryParams.push(type);
    }

    if (cost) {
        queryText += ' AND cost = $' + (queryParams.length + 1);
        queryParams.push(cost);
    }

    if (trait) {
        queryText += ' AND trait ILIKE $' + (queryParams.length + 1);
        queryParams.push(`%${trait}%`);
    }

    if (rarity) {
        queryText += ' AND rarity = $' + (queryParams.length + 1);
        queryParams.push(rarity);
    }

    if (effect) {
        queryText += ' AND effects ILIKE $' + (queryParams.length + 1);
        queryParams.push(`%${effect}%`);
    }
    
    if (cardSet) {
        queryText += ' AND release_set = $' + (queryParams.length + 1);
        queryParams.push(cardSet);
    }

    if (sortBy) {
        const validSortColumns = ['card_name', 'type', 'cost', 'trait', 'rarity', 'effects', 'cardSet'];
        if (validSortColumns.includes(sortBy)) {
            const order = sortOrder === 'DESC' ? 'DESC' : 'ASC';
            queryText += ` ORDER BY ${sortBy} ${order}`;
        } else {
            return res.status(400).json({ error: 'Invalid sort condition' });
        }
    }

    const result = await pool.query(queryText, queryParams);
    console.log(result.rows);
    return result.rows;
}


module.exports = {
    getCards,
    getCardsFiltered
};