const express = require('express')
const app = express()
const config = require('./config/config');
const userRoutes = require('./routes/userRoutes');
const paperRoutes = require('./routes/generatePaperRoutes');
// const auth = require('./middlewares/authAccess');
const rateLimit = require('express-rate-limit');
const helmet = require('helmet');
const mongoose = require('mongoose');
const authRoutes = require('./routes/authRoutes');

require('dotenv').config();

mongoose
  .connect(config.db.url)
  .then(() => console.log('Connected to MongoDB'))
  .catch((err) => console.error('Error connecting to MongoDB', err));



app.use(express.json({limit: '10mb'})); // to support JSON-encoded bodies
app.use(helmet()); // Helmet helps you secure your Express apps by setting various HTTP headers.

const limiter = rateLimit({
  windowMs: 10 * 60 * 1000, // 10 minutes
  max: 100, // limit each IP to 100 requests per windowMs
});
app.use(limiter); //  apply to all requests
app.use("/api/user",userRoutes); 
app.use("/api/paper",paperRoutes);
app.use("/api/authentication",authRoutes);




app.get('/', (req, res) => {
    res.send('Welcome to Twinklebun API Gateway!')
});


const PORT = config.port;

app.listen(config.port, () => {
  console.log(`Server is running on port ${PORT}`);
});