const jwt = require('jsonwebtoken');
require('dotenv').config();



module.exports = function(req,res,next){

    if (req.headers.authorization && req.headers.authorization.startsWith('Bearer')) {
        const token = req.headers.authorization.split(' ')[1];
        if (!token) {
            return res.sendStatus(401);
        }

        jwt.verify(token, process.env.SECRET_KEY, (err, user) => {
            if (err) {
                return res.sendStatus(403);
            }
            req.user = user;
            next();
        });
    } else {
        return res.status(401).send('Access denied. No token provided');
    }

}



