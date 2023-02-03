import mongoose from 'mongoose';
const Schema = mongoose.Schema;

const productSchema = new Schema({
    name: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    },
    colour: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    price: {
        type: String,
        required: true
    },
    startingDateAvailable: {
        type: String,
        required: true
    },
    type: {
        type: String,
        required: true
    },
    isOnSale: {
        type: Boolean
    },
    stock: {
        type: Number,
        required: true
    },
    endingDateAvailable: {
        type: String
    },
    manufacturer: {
        type: String,
        required: true
    }
}, {timestamps: true});


const Product = mongoose.model('Product', productSchema);

export default Product;