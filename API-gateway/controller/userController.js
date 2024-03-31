const User = require('../models/userModel');

const userInfor = async (req, res) =>{
    try{
        const userId = req.userId;
        const user  = await User.findById(userId);
        const name = user.name;
        const email = user.email;
        const overallScore = user.overallScore;
        const multiplicationScore = user.multiplicationScore;

        res.status(200).json({name, email, overallScore, multiplicationScore});
    }catch(error){
        res.status(500).json({message: 'Internal server error'});
    }   
}

module.exports = {
    userInfor
};