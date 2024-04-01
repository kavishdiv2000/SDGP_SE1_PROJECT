const User = require('../models/userModel');
const { generateAccessToken,generateRefreshToken,verifyRefreshToken} = require('../config/jwt');
const config = require("../config/config")
const bcrypt = require('bcrypt');
const mongoose = require('mongoose')

const signup = async (req, res) => {
  try {
    const { name, email, password } = req.body;

    // Check if the user already exists
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ message: 'User already exists' });
    }

    // Hash the password
    const salt = await bcrypt.genSalt(Number(config.salt));
    const hashedPassword = await bcrypt.hash(password, salt);

    // Create a new user
    const newUser = new User({ name,email, password: hashedPassword });
    
    await newUser.save();
    

    res.status(201).json({ message: 'User created successfully' });
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: 'Internal server error' });
  }
};

const signin = async (req, res) => {

  try{
    const { email, password } = req.body;
    const user = await User.findOne({ email });

    if (!user) {
      return res.status(401).json({ message: 'Invalid email or password' });
    }

    const isValidPassword = await user.comparePassword(password);

    if (!isValidPassword) {
      return res.status(401).json({ message: 'Invalid email or password' });
    }

    const accessToken = generateAccessToken({ userId: user._id });
    const refreshToken = generateRefreshToken({ userId: user._id });

    const salt = await bcrypt.genSalt(Number(config.salt));
    const hashedrefreshToken = await bcrypt.hash(refreshToken, salt);
    user.refreshToken = hashedrefreshToken;
    await user.save();

    return res.json({ accessToken, refreshToken});

  } catch (error) {
    res.status(500).json({ message: 'Internal server error' });
  };
}



const authenticateRefreshToken =async (req, res) => {
  try {
    const { refreshToken } = req.body;
    
    if (!refreshToken) {
      return res.status(401).json({ message: 'Access denied, token missing!' });
    }
    

    try {
      const decoded = verifyRefreshToken(refreshToken);
      req.userId = decoded.userId;
      
    } catch (error) {
      return res.status(403).json({ message: 'Invalid refresh token' });
    }
    const user = await User.findOne({ _id: req.userId});

    const isValidRefreshToken = await user.compareRefreshToken(refreshToken);

    if (!user && !isValidRefreshToken) {
      return res.status(403).json({ message: 'Invalid refresh token' });
    }
    if(user && !isValidRefreshToken){
      return res.status(401).json({ message: 'login again' });
    }


    const accessToken = generateAccessToken({ userId: user._id });
    
    res.json({ accessToken });
  } catch (error) {
    res.status(500).json({ message: 'Internal server error' });
  }
}

module.exports = {
  signup,
  signin,
  authenticateRefreshToken
};