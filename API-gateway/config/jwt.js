const jwt = require('jsonwebtoken');
const config = require('./config');

const generateToken = (payload) => {
  return jwt.sign(payload, config.jwt.accessSecret, { expiresIn: config.jwt.expiresIn });
};

const verifyToken = (token) => {
    
  return jwt.verify(token, config.jwt.accessSecret);
};

module.exports = {
  generateToken,
  verifyToken
};