import express from 'express';
import {ObjectId} from 'mongodb';
import Product from '../models/productModel.js';

const router = express.Router();

router.delete('/Products/:id', (req, res) => {
    Product.deleteOne({_id: ObjectId(req.params.id)})
        .then(result => {
            console.log(res);
            res.json(result);
        })
        .catch(err => {
            res.status(500).send(err.message);
        });;
});

export default router;