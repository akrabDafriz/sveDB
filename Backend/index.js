const express = require("express");
const bodyParser = require("body-parser");
const cors = require('cors');
const cardsRoutes = require("./src/cards/cards.routes");
const deckRoutes = require("./src/deck/deck.routes");
const listRoutes = require("./src/list/list.routes");
const userRoutes = require("./src/user/user.routes");
const pool = require("./src/db");

const port = 9194;
const app = express();

app.use(bodyParser.json());

//middleware
//app.use(cors());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(port, () => {
    console.log("🚀 Server is running and listening on port", port);
});

app.use('/cards', cardsRoutes);
app.use('/decks', deckRoutes);
app.use('/list', listRoutes);
app.use('/user', userRoutes);

pool.connect().then(() => {
    console.log("🥵 Connected to PostgreSQL database");
}).catch((err) => {
    console.error("Error connecting to database:", err.message);
});