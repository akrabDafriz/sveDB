const pool = require('../db');
const bcrypt = require('bcrypt');

async function hashPassword(password){
    const saltRounds = 10; 
    const hashedPassword = await bcrypt.hash(password, saltRounds);
    return hashedPassword;
}

async function checkPassword(password, hashedPassword){
    const match = await bcrypt.compare(password, hashedPassword);
    return match;
}

async function registerUser(userDetails){
    const { username, email, password } = userDetails;
    if (username.length > 30) throw new Error(`Username length cannot be more than 30`);
    if (password.length > 20) throw new Error(`Password length cannot be more than 20`);
    const emailExists = await pool.query(`SELECT * FROM a_user WHERE email = $1`, [email]);
    if (emailExists.rows.length !== 0) throw new Error(`Email is unavailable`);
    const hashedPassword = await hashPassword(password);
    const result = await pool.query(
        'INSERT INTO a_user (username, email, password) VALUES ($1, $2, $3) RETURNING id',
        [username, email, hashedPassword]
    );
    
    return result.rows[0].id;
}

async function loginUser(userDetails){
    console.log(userDetails);
    const { email, password } = userDetails;
    const result = await pool.query(
        'SELECT * FROM a_user WHERE email = $1',
        [email]
    );
    if (result.rows.length == 0) {
        return {
            id: -1,
            username: " ",
            email: " ",
            password: " "
        };
    }
    const user = result.rows[0];
    const isPasswordValid = await checkPassword(password, user.password);
    if (!isPasswordValid) {
        return {
            id: -2,
            username: " ",
            email: " ",
            password: " "
        };
    }
    return user;
}

module.exports = {
    registerUser,
    loginUser
};