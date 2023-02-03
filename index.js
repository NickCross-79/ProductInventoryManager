import express from 'express';
import mongoose from 'mongoose';
import getRoutes from './routes/getRoutes.js';
import deleteRoutes from './routes/deleteRoutes.js';
import patchRoutes from './routes/patchRoutes.js';
import putRoutes from './routes/putRoutes.js'

const app = express();
mongoose.connect('mongodb://localhost/ProductCatalog')
    .then((result) => {
        console.log("Connected to database");
        app.listen(3000);
    });

app.use(getRoutes);
app.use(deleteRoutes);
app.use(patchRoutes);
app.use(putRoutes);

app.use((req,res) => {
    console.log('new request made');
    console.log('host: ', req.hostname);
    console.log('path: ', req.path);
    console.log('method: ', req.method);
});
