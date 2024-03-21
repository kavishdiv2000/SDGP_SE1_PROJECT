const generateQuestionsAndAnswers = require('../services/openaiService');

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

  // openaiService.generateQuestionsAndAnswers(content, numOfQuestions)
  // .then(data => {
  //   // console.log('Fetched data:', data);
  //   res.status(200).json({ data})
  // })
  // .catch(error => {
  //   // console.error('Error:', error);
  //   res.status(500).json({ message: 'Error processing content' });
  //   // Handle the error
  // });

  try {
    const response = await generateQuestionsAndAnswers(content, numOfQuestions);
    res.status(200).json({response});
  } catch (error) {
    console.log(error)
    res.status(500).json({ message: 'Error processing content' });
    
  }
};

module.exports = {
  processContent
};