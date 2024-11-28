import dotenv from 'dotenv';
import express from 'express';
import connectDb from './src/config/mongoDb.js';
import reviewRoutes from './src/routes/reviewRoutes.js';
import { startEurekaClient } from './src/config/eurekaClient.js'
import swaggerUi from 'swagger-ui-express';
// import swaggerDocument from './swagger-output.json'
import swaggerDocument from './swagger-output.json' assert { type: 'json' };

dotenv.config();
const app = express();
connectDb();
app.use(express.json());

app.use('/api/review', reviewRoutes);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

app.use((err, req, res, next) => {
    const statusCode = err.status || 500; // Default to 500 if no status is provided
    res.status(statusCode).json({
        error: {
            message: err.message || "Internal Server Error",
            status: statusCode,
        },
    });
});
const PORT = process.env.PORT || 5300;
// startEurekaClient();
app.listen(PORT, () => {
    console.log(`Server Running on port :${PORT}`);
})