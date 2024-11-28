import swaggerAutogen from 'swagger-autogen';

const doc = {
    info: {
        title: 'Delivery API',
        description: 'API for managing deliveries',
    },
    host: 'localhost:5200',
    schemes: ['http'],
    tags: [
        {
            name: 'Delivery',
            description: 'Endpoints related to delivery management',
        },
        {
            name: 'Order',
            description: 'Endpoints related to order processing',
        },
    ],
};

const outputFile = './swagger-output.json';
const routes = ['./src/routes/deliveryRoutes.js'];

swaggerAutogen(outputFile, routes, doc);
