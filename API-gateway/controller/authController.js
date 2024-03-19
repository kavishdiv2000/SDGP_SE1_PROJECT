const User = require('../models/userModel');
const { generateToken } = require('../config/jwt');

const signup = async (req, res) => {
  // Handle user signup
};

const signin = async (req, res) => {
  // Handle user signin
  const { email, password } = req.body;
  const user = await User.findOne({ email });

  if (!user) {
    return res.status(401).json({ message: 'Invalid email or password' });
  }

  const isValidPassword = await user.comparePassword(password);

  if (!isValidPassword) {
    return res.status(401).json({ message: 'Invalid email or password' });
  }

  const token = generateToken({ userId: user._id });
  return res.json({ token });
};

module.exports = {
  signup,
  signin
};