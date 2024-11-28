import dotenv from 'dotenv'
import express from 'express';
import connectDb from './src/config/mongoDb.js'
import deliveryAgentRoutes from './src/routes/deliveryAgentRoutes.js'
import { startEurekaClient } from './src/config/eurekaClient.js'
import swaggerUi from 'swagger-ui-express';
// import swaggerDocument from './swagger-output.json'
import swaggerDocument from './swagger-output.json' assert { type: 'json' };

dotenv.config();
const app = express();
connectDb();
app.use(express.json());
app.use('/api/agent', deliveryAgentRoutes);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));
const PORT = process.env.PORT || 5100
startEurekaClient();
app.listen(PORT, () => {
    console.log(`Server Running on port :${PORT}`);

})