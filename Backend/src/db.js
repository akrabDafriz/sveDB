//const { Pool } = require("pg");

// const pool = new Pool({
//     user: "postgres",
//     password: "postgres",
//     host: null,
//     database: "svedbtest"
// });

require("dotenv").config();
const { Pool } = require("pg");
const winston = require("winston");

// Logger setup
const logger = winston.createLogger({
  level: "info",
  format: winston.format.combine(
    winston.format.timestamp(),
    winston.format.json()
  ),
  transports: [new winston.transports.Console()],
});

let { PGHOST, PGDATABASE, PGUSER, PGPASSWORD } = process.env;

const pool = new Pool({
  user: PGUSER,
  password: PGPASSWORD,
  host: PGHOST,
  database: PGDATABASE,
  idleTimeoutMillis: 30000,
  connectionTimeoutMillis: 2000,
  max: 20,
  keepAlive: true,
});

const connectWithRetry = () => {
  pool
    .connect()
    .then((client) => {
      logger.info("Connected to the database");
      client.release();
    })
    .catch((error) => {
      logger.error("Error connecting to the database:", error);
      logger.info("Retrying connection in 5 seconds...");
      setTimeout(connectWithRetry, 5000); // retry after 5 seconds
    });
};

connectWithRetry();

const query = async (text, params) => {
  const start = Date.now();
  try {
    const res = await pool.query(text, params);
    const duration = Date.now() - start;
    logger.info("Executed query", {
      text,
      params,
      duration,
      rows: res.rowCount,
    });
    return res;
  } catch (error) {
    logger.error("Query error", { text, params, error });
    throw error;
  }
};

module.exports = { query };

module.exports = pool;
