import express from 'express';
import {ObjectId} from 'mongodb';
import Product from '../models/productModel.js';

const router = express.Router();
router.use(express.json());

router.patch('/Products/:id', (req, res) => {
    console.log('/Products/'+req.params.id);
    Product.updateOne({_id: ObjectId(req.params.id)}, {$set: req.body})
        .then(result => {
            console.log(result);
            res.json(result);
        })
        .catch(err => {
            res.status(500).send(err.message);
        });
});

export default router;