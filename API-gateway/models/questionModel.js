const mongoose = require('mongoose');

const option = mongoose.Schema({
    1: { type: String, required: true },
    2: { type: String, required: true },
    3: { type: String, required: true },
    4: { type: String, required: true }
})


module.exports = mongoose.Schema({
    
    question: { type: String, required: true },
    options: { type: option, required: true,},
    correct_answer: { type: String, required: true, }
    
})