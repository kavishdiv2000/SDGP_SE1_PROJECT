const express = require('express')
const app = express()
require('dotenv').config();

app.get('/', (req, res) => {
    res.send('Hello World!')
});

app.get('/hello', (req, res) => {
    res.send('hi there!!')
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});