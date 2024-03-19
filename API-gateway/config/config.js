require('dotenv').config();

module.exports = {
    port: process.env.PORT || 3000,
    db: {
      url: process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/'
    },
    openai: {
      apiKey: process.env.OPENAI_API_KEY
    },
    jwt: {
      accessSecret: process.env.JWT_ACCESS_SECRET,
      accessExpiresIn: process.env.JWT_ACCESS_EXPIRES_IN,
      refreshSecret: process.env.JWT_REFRESH_SECRET,
      refreshExpiresIn: process.env.JWT_REFRESH_EXPIRES_IN

    }
  };