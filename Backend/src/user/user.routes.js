const express = require('express');
const router = express.Router();
const userController = require('./user.controller');

router.post('/register', userController.registerUser);
router.post('/login', userController.loginUser);
router.put('/update', userController.editProfile);

module.exports = router;    