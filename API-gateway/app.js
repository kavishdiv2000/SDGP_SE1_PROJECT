const express = require('express')
const app = express()
const config = require('./config/config');
const user = require('./routes/user');
const auth = require('./middleware/authAccess');
const rateLimit = require('express-rate-limit');
const helmet = require('helmet');

require('dotenv').config();

app.use(express.json({limit: '10mb'})); // to support JSON-encoded bodies
app.use(helmet()); // Helmet helps you secure your Express apps by setting various HTTP headers.

const limiter = rateLimit({
  windowMs: 10 * 60 * 1000, // 10 minutes
  max: 100, // limit each IP to 100 requests per windowMs
});
app.use(limiter); //  apply to all requests
app.use("/api/user",auth,user); 


app.get('/', (req, res) => {
    res.send('Welcome to Twinklebun API Gateway!')
});


const PORT = config.port;

app.listen(config.port, () => {
  console.log(`Server is running on port ${PORT}`);
});