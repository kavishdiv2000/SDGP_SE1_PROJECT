const authController = require('../controller/authController');

const express = require('express');
const router = express.Router();

router.post('/signup', authController.signup);
router.post('/login', authController.signin);
router.post('/refresh', authController.authenticateRefreshToken);

module.exports = router;