const express = require('express')
const app = express()
const user = require('./routes/user');
const auth = require('./middleware/authAcess');
const rateLimit = require('express-rate-limit');
const helmet = require('helmet');

require('dotenv').config();

app.use(express.json({limit: '10mb'})); // to support JSON-encoded bodies
app.use(helmet()); // Helmet helps you secure your Express apps by setting various HTTP headers.

const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100, // limit each IP to 100 requests per windowMs
});
app.use(limiter); //  apply to all requests
app.use("/api/user",auth,user); 



app.get('/', (req, res) => {
    res.send('Welcome to Twinklebun API Gateway!')
});




const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});