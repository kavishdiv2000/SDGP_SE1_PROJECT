const User = require('../models/userModel');


const reviseHubScoreUpdate = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);  

        const paperId = req.query.id;
        console.log(paperId);
        var paper = user.reviseHub.id(paperId);
        if(!paper){
            return res.status(404).json({message: 'Paper not found'});
        }
        paper.score = paperId;
        await user.save();
        res.status(200).json({message: 'Score updated'});
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



module.exports = {
    reviseHubScoreUpdate,
    totalScoreUpdate
};