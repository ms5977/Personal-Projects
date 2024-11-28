import express from 'express';
import dotenv from 'dotenv';
import connectDb from './src/config/mongoDb.js'
import deliveryRoutes from './src/routes/deliveryRoutes.js'
import { startEurekaClient } from './src/config/eurekaClient.js'
import swaggerUi from 'swagger-ui-express';
// import swaggerDocument from './swagger-output.json'
import swaggerDocument from './swagger-output.json' assert { type: 'json' };

dotenv.config();
const app = express();
connectDb();
app.use(express.json());
app.use('/api/delivery', deliveryRoutes)
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));
const PORT = process.env.PORT || 5200
// startEurekaClient();
app.listen(PORT, () => {
    console.log(`Server Running on Port ${PORT}`);
})