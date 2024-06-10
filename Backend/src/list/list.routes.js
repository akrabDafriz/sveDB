const express = require('express');
const router = express.Router();
const listController = require('./list.controller');

router.post('/create', listController.createList);
router.get('/', listController.getAllLists);
router.get('/cards', listController.getListByName);

module.exports = router;