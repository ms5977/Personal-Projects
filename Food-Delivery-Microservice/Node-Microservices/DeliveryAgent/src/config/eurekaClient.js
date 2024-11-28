import { Eureka } from 'eureka-js-client';

// Create an instance of the Eureka client
const client = new Eureka({
    instance: {
        instanceId: 'DeliveryAgentService',
        app: 'DeliveryAgentService', // app name as string
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        port: {
            '$': 5100, // port should be an object with `$` property for non-secure port
            '@enabled': true // enable the port
        },
        vipAddress: 'DeliveryAgentService',
        secureVipAddress: 'DeliveryAgentService',
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn'
        }
    },
    eureka: {
        host: '127.0.0.1',
        port: 8080,
        // servicePath: '/eureka/app/', // path to the eureka apps
        // maxRetries: 10, // optional: max retries in case of failure
        // requestRetryDelay: 5000 // optional: retry delay in milliseconds
    }
});

const startEurekaClient = () => {
    client.start(error => {
        if (error) {
            console.error('Error starting Eureka client:', error);
        } else {
            console.log('Eureka client started and registered with Eureka server');
        }
    });
};

export { startEurekaClient };