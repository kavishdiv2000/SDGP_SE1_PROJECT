const jwt = require('jsonwebtoken');
require('dotenv').config();



const tempAuth  = (req,res,next)=>{


    if(req.headers.authorization && req.headers.authorization.startsWith('bearer')){
        const token = req.headers.authorization.split(' ')[1];
        if(token==null)res.sendStatus(401);

        jwt.verify(token, process.env.TEMP_JWT_SECRET,(err,user)=>{
            if(err) res.sendStatus(403);
            // req.user = user;
            next();
        })}

}

module.exports = tempAuth;


