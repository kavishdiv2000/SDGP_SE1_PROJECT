const User = require('../models/userModel');

const leadershipBoard = async (req, res) =>{
    try{
        const userId = req.userId;
        const user = await User.findById(userId);
        if(!user){
            return res.status(404).json({message: 'User not found'});
        }
        const rank = await User.find({overallScore: {$gt: user.overallScore}}).countDocuments()+1;

        const users = await User.find({}).sort({overallScore: -1}).limit(10);
        const leaders = users.map(user => {return {"name":user.name, "overallScore":user.overallScore, "id":user._id}});

        

        if(rank>9){
            leaders[9] = {"name":user.name, "overallScore":user.overallScore, "id":user._id};
            return res.status(200).json({"leaders":leaders, "rank":rank, "index":9});
        }else{
            leaders.sort((a, b) => b.score - a.score);
            let index = leaders.findIndex(currentUser =>{ return currentUser.id+" "=== user._id+" ";});
            if (index === -1){
                index = rank-1;
                leaders.splice(index, 0, {"name":user.name, "overallScore":user.overallScore, "id":user._id})
            }
            return res.status(200).json({"leaders":leaders, "rank":rank, "index":index});
        }
        
    }catch(error){
        console.log(error);
        res.status(500).json({message: 'Internal server error'});
    }
}

module.exports = {
    leadershipBoard
};



