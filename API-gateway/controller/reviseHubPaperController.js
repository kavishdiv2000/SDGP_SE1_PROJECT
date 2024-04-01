const User = require('../models/userModel');


const paperList = async (req, res) =>{
    try{
        const userId = req.userId;
        const user = await User.findById(userId);
        const papers = user.reviseHub;
        const paperList = papers.map(paper => {return {"id":paper._id ,"title":paper.title, "num":paper.num, "score":paper.score}});
        res.status(200).json({paperList});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }

}

const revisePaper = async (req, res) =>{
    try{
        const userId = req.userId;
        const user = await User.findById(userId);
        // const paperId = req.params.paperId;
        const paperId = req.query.id;
        console.log(paperId);
        const paper = user.reviseHub.id(paperId);
        if(!paper){
            return res.status(404).json({message: 'Paper not found'});
        }
        const {title, num, score, questions} = paper;
        res.status(200).json({title, num, score, questions});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }
}

const submitPaper = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        // const paperId = req.params.paperId;
        const paperId = req.query.id;
        const paper = user.reviseHub.id(paperId);
        if(!paper){
            return res.status(404).json({message: 'Paper not found'});
        }
        const questions = paper.questions;
        const answers = req.body.answers;
        if(!answers){
            return res.status(400).json({message: 'Answers are required'});
        }
        if(answers.length !== questions.length){
            return res.status(400).json({message: 'Invalid number of answers'});
        }
        let totalScore = 0;
        for(let i=0; i<questions.length; i++){
            if(answers[i] === questions[i].answer){
                totalScore++;
            }
        }
        paper.score = totalScore;
        await user.save();
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }
}

module.exports = {
    paperList,
    revisePaper,
    submitPaper
};