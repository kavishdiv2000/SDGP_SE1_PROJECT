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


  let refreshTokens = [];
  router.get('/login', (req, res) => {
    //DB
    //OK
    const username = req.body.username;
    const user = {name: username};

    console.log("user",user);
    const accessToken = jwt.sign(user,process.env.SECRET_KEY,{expiresIn:'1h'})
    const refreshToken = jwt.sign(user,process.env.REFRESH_TOKEN_SECRET,{expiresIn:'10d'})
    refreshTokens.push(refreshToken);
    res.send({accessToken,refreshToken});
  })


  router.post('/token', (req, res) => {
    const refreshToken = req.body.refreshToken;
    if(refreshToken == null) return res.sendStatus(401);
    if(!refreshTokens.includes(refreshToken)) return res.sendStatus(403);
    jwt.verify(refreshToken,process.env.REFRESH_TOKEN_SECRET,(err,user)=>{
      if(err) return res.sendStatus(403);
      const accessToken = generateAccessToken({name: user.name});
      res.json({accessToken:accessToken});
    })
  })


module.exports = router;