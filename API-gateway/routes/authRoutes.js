const authController = require('../controller/authController');

const express = require('express');
const router = express.Router();

router.post('/signup', authController.signup);
router.get("/signin",(req,res)=>{
    res.send("this is the get route for signin in twinklebun")
})
router.post('/login', authController.signin);
router.post('/refresh', authController.authenticateRefreshToken);

module.exports = router;