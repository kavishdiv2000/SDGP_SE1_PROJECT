const express = require ('express');
const userController = require('../controller/userController');
const authMiddleware = require('../middlewares/authAccess');
const reviseHubPaperController = require('../controller/reviseHubPaperController');
const scoreController = require('../controller/scoreController');
const leadershipBoardController = require('../controller/leadershipBoardController');
require('dotenv').config();


const router = express.Router();


router.put('/updatescore',authMiddleware,scoreController.totalScoreUpdate);
router.put('/revise-hub-score-update',authMiddleware,scoreController.reviseHubScoreUpdate);
router.get('/profile',authMiddleware,userController.userInfor);
router.get('/revisepaperlist',authMiddleware,reviseHubPaperController.paperList);
router.get('/revisepaper',authMiddleware,reviseHubPaperController.revisePaper);
router.post('/submitpaper/:id',authMiddleware,reviseHubPaperController.submitPaper);
router.get('/leadership-rank',authMiddleware,leadershipBoardController.leadershipBoard);
router.put('/updatemultiplicationscore',authMiddleware,scoreController.multilicationHighScoreUpdate);


module.exports = router;