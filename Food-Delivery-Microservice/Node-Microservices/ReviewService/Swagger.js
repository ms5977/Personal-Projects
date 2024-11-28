import swaggerAutogen from 'swagger-autogen';

const doc = {
    info: {
        title: 'Order API',
        description: 'API for managing Orders',
    },
    host: 'localhost:5300',
    schemes: ['http'],
    tags: [
        {
            name: 'Order',
            description: 'Endpoints related to Order management',
        },
    ],
};

const outputFile = './swagger-output.json';
const routes = ['./src/routes/reviewRoutes.js'];

swaggerAutogen(outputFile, routes, doc);
