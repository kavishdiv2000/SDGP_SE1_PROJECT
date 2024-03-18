require('dotenv').config();

module.exports = {
    secretAccess: process.env.ACCESS_SECRET_KEY || 'HHSSBDJJSJHDJDBWSJDJWJWNjsnbsjdbs',
    secretRefresh: process.env.REFRESH_SECRET_KEY || 'hsjshdjssjnsnjbijwjcbkqwcbbjbqjbjq',
    expiresIn: '1d', // JWT expiration time (e.g., 1 day)
  };