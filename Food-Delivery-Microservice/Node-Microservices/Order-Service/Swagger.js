import swaggerAutogen from 'swagger-autogen';

// const doc = {
//     info: {
//         title: 'Order API',
//         description: 'API for managing Orders',
//     },
//     host: 'localhost:5000/api/orders',
//     schemes: ['http'],
//     tags: [
//         {
//             name: 'Order',
//             description: 'Endpoints for  Order management',
//         },
//     ],
// };

const doc = {
    openapi: '3.0.0',
    info: {
        title: 'Express API for JSONPlaceholder',
        version: '1.0.0',
        description:
            'This is a REST API application made with Express. It retrieves data from JSONPlaceholder.',
        license: {
            name: 'Licensed Under MIT',
            url: 'https://spdx.org/licenses/MIT.html',
        },
        contact: {
            name: 'JSONPlaceholder',
            url: 'https://jsonplaceholder.typicode.com',
        },
    },
    servers: [
        {
            url: 'http://localhost:3000',
            description: 'Development server',
        },
    ],
};
const outputFile = './swagger-output.json';
const routes = ['./src/routes/orderRoutes.js'];

swaggerAutogen(outputFile, routes, doc);
