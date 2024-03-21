const express = require ('express');
const openaiController = require('../controller/openaiController');
const authMiddleware = require('../middlewares/authAccess');
const tempAuth = require('../middlewares/authTemp');
require('dotenv').config();


const router = express.Router();


router.get('/process-generate-content', tempAuth, openaiController.processContent);

module.exports = router;