const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const paperModel = require('./paperModel');

const userSchema = new mongoose.Schema({
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  overallScore: { type: Number, default: 0 },
  multiplicationScore: { type: Number, default: 0 },
  reviseHubScore:{ type: Number, default: 0 },
  scholarshipPaperScore:[Number],
  reviseHub:[paperModel]
  
});



userSchema.methods.comparePassword = async function (candidatePassword) {
  return await bcrypt.compare(candidatePassword, this.password);
};

const User = mongoose.model('User', userSchema);

module.exports = User;