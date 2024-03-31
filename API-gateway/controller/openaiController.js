const generateQuestionsAndAnswers = require('../services/openaiService');
const User = require('../models/userModel');

const processContent = async (req, res) => {
  
  const content  = req.body.data;
  const numOfQuestions = req.body.num;
  const a = parseInt(numOfQuestions);

  if (typeof a !== "number" || a<1 || a>10 || isNaN(a)) {
    return res.status(400).json({ message: 'Invalid number of questions' });
  }

  if (!content) {
    return res.status(400).json({ message: 'Content is required' });
  }

  if (content.trim().length > 3000) {
    return res.status(400).json({ message: 'Content should be less than 3000 characters' });
  }
  if (content.trim().length < 200) {
    return res.status(400).json({ message: 'Content should be more than 200 characters' });
  }

  const splitContent = content.split(' ');
  if(splitContent.length < 50){
    return res.status(400).json({ message: 'Content should be more than 50 words' });
  }
  const newContent = splitContent.join(' ');
  const title = splitContent.slice(0, 5).join(' ');

 
  try {
    const response = await generateQuestionsAndAnswers(newContent, numOfQuestions);
    const userId = req.userId;
    const user = await User.findById(userId);
    user.reviseHub.push({
      title: title,
      num: a,
      score: 0, 
      questions: response});
    
    await user.save();

    res.status(200).json({questions:response});
  } catch (error) {
    console.log(error)
    res.status(500).json({ message: 'Error processing content' });
    
  }
};

module.exports = {
  processContent
};