const User = require('../models/userModel');


const reviseHubScoreUpdate = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        const paperScore = req.body.score;


        // const paperId = req.query.id;
        console.log(paperId);
        // var paper = user.reviseHub.id(paperId);
        // if(!paper){
        //     return res.status(404).json({message: 'Paper not found'});
        // }
        // paper.score = paperId;
        user.reviseHubScore = user.reviseHubScore + paperScore;
        await user.save();
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }

}


const totalScoreUpdate = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        const totalScore = user.reviseHubScore + user.multiplicationScore;
        user.overallScore = totalScore;
        await user.save();
        res.status(200).json({totalScore});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }   
}

const totalScore = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        const totalScore = user.overallScore;
        res.status(200).json({totalScore});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }   
}


const multilicationHighScoreUpdate = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        if(req.body.score > user.multiplicationScore){
            user.multiplicationScore = req.body.score;
            await user.save();
        }
        res.status(200).json({message: 'High score updated'});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }
}





module.exports = {
    reviseHubScoreUpdate,
    totalScoreUpdate,
    multilicationHighScoreUpdate,
    totalScore
};