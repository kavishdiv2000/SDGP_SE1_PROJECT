
const { verifyAccessToken  } = require('../config/jwt');
require('dotenv').config();

const authJWT  = (req,res,next)=>{

    if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
        const token = req.headers.authorization.split(' ')[1];
        if (!token) {
            return res.sendStatus(401);
        }
        
        try {
            const decoded = verifyAccessToken(token);
            req.userId = decoded.userId;
            
            next();
          } catch (error) {
            return res.status(403).json({ message: 'Invalid token' });
          }

          
    } else {
        return res.status(401).json({message:'Access denied. No token provided'});
    }

}

module.exports = authJWT;



