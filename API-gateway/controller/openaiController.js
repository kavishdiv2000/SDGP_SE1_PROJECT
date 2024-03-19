const openaiService = require('../services/openaiService');

const processContent = async (req, res) => {
  
  const content  = req.body.data;
  const numOfQuestions = req.body.num;

  try {
    const response = await openaiService.generateQuestionsAndAnswers(content, numOfQuestions);
    res.status(200).json({response});
  } catch (error) {
    res.status(500).json({ message: 'Error processing content' });
  }
};

module.exports = {
  processContent
};