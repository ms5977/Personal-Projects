import swaggerAutogen from 'swagger-autogen';

const doc = {
    info: {
        title: 'Delivery Agent API',
        description: 'API for managing Delivery Agent',
    },
    host: 'localhost:5100/api/agent',
    schemes: ['http'],
    tags: [
        {
            name: 'Delivery',
            description: 'Endpoints related for delivery agent management',
        },
    ],
};

const outputFile = './swagger-output.json';
const routes = ['./src/routes/deliveryAgentRoutes.js'];

swaggerAutogen(outputFile, routes, doc);
