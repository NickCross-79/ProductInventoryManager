import express from 'express';
import {ObjectId} from 'mongodb';
import Product from '../models/productModel.js';

const router = express.Router();

router.get('/Products', (req, res) => {
    console.log('/Products');
    Product.find()
        .then( result => {
            console.log(result);
            res.send(result);
        });
});

router.get('/Products/identifiers', (req, res) => {
    console.log('/Products/identifiers');
    Product.find({}, {_id: 1})
        .then( result => {
            console.log(result);
            res.send(result);
        });
});

router.get('/Products/names', (req, res) => {
    console.log('/Products/names');
    Product.find({}, {name: 1})
        .then( result => {
            console.log(result);
            res.json(result);
        });
});

router.get('/Products/:name', (req, res) => {
    console.log('/Products/:name');
    Product.findOne({name: req.params.name})
        .then( result => {
            console.log(result);
            res.send(result);
        });
});

router.get('/Products/:id', (req, res) => {
    console.log('/Products/:id');
    Product.findOne({_id: ObjectId(req.params.id)})
        .then( result => {
            console.log(result);
            res.send(result);
        });
});

router.get('/Products/images/:name', (req, res) => {
    console.log('/Products/images/:name');

    Product.findOne({name: req.params.name}, {image: 1, _id: 0})
        .then( result => {
            console.log(result.image);
            res.sendFile(result.image, {root:'.'});
        });
});

router.get('/Products/:id/:field', (req, res) => {
    console.log('/Products/:id/:field');
    
    Product.findOne({_id: ObjectId(req.params.id)}, {[req.params.field]: 1, _id: 0})
        .then( result => {
            console.log(result);
            res.send(result);
        });
});

router.get('/Products/Page/:skip/:limit', (req, res) => {
    console.log('/Products/Page/:skip/:limit');

    Product.find()
    .skip(req.params.skip)
    .limit(req.params.limit)
    .then(result => {
        console.log(result);
        res.send(result);
    });
});

export default router;
