const express = require ('express');
const jwt = require('jsonwebtoken');
require('dotenv').config();


const router = express.Router();


router.route('/')
.get((req, res) => {
    res.send({"msg":`router is working`, "user":req.user})
  })
.post((req, res) => {
    res.send({"msg":"User router is working post"})
  })

// router.get('/', (req, res) => {
//   res.send({"msg":`router is working`, "user":req.user})
// })


// router.post('/', (req, res) => {
//     res.send({"msg":"User router is working post"})
//   })

  router.get('/add', (req, res) => {
    res.send({"msg":"User added"})
  })


  


module.exports = router;