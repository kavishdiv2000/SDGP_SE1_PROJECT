const mongoose = require('mongoose');
const questionModel = require('./questionModel');

module.exports = mongoose.Schema({
    title: { type: String, required: true },
    num: { type: Number, required: true },
    score: { type: Number,default: 0},
    questions: [questionModel]
})
