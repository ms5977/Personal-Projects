import express from 'express';
import dotenv from 'dotenv';
import connectDB from './src/config/MongoDb.js'
import orderRoutes from './src/routes/orderRoutes.js'
import { startEurekaClient } from './src/config/eurekaClient.js';
import swaggerUi from 'swagger-ui-express';
import swaggerDocument from './swagger-output.json' assert { type: 'json' };


// const express = require('express');
// const dotenv = require('dotenv');
// const connectDB = require('./src/config/MongoDb');
// const orderRoutes = require('./src/routes/orderRoutes')


dotenv.config();//Load the enviorment file

const app = express();

// connect to DB
connectDB();

// Middleware
app.use(express.json());

//Routes
app.use('/api/orders', orderRoutes);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

const PORT = process.env.PORT || 5000;
// startEurekaClient();

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);

})
