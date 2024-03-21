const { Configuration, OpenAI } = require('openai');
const config = require('../config/config');

// Configure OpenAI API
const openai = new OpenAI({
    apiKey: config.openai.apiKey, 
  });


async function generateQuestionsAndAnswers(content, numOfQuestions) {
   
  
    const prompt = `Generate ${numOfQuestions} multiple choice questions with answers based on the following content:
    
    ${content}
    
    Each question should have 4 options, with one being the correct answer. Present the questions and answers in the following format:
    
    '[
        {
          "question": "Question text",
          "options": {
            "1": "option 1",
            "2": "option 2",
            "3": "option 3",
            "4": "option 4"
          },
          "correct_answer": "2"
        },
        {
            "question": "Question text",
            "options": {
              "1": "option 1",
              "2": "option 2",
              "3": "option 3",
              "4": "option 4"
            },
            "correct_answer": "3"
        },
    ...]'`;
      
    // gpt-3.5-turbo-instruct
        const response = await openai.completions.create({
          model: 'gpt-3.5-turbo-instruct',
          prompt: prompt,
          max_tokens: 1000,
          temperature: 0.7,
        });
    
        const generatedMCQ = response.choices[0].text;
        // console.log(response);
        // console.log(generatedMCQ);
        const jsonFormate = JSON.parse(generatedMCQ)

        // res.status(200).json(jsonFormate);

        return jsonFormate;
       
}

module.exports = generateQuestionsAndAnswers;