import express from 'express';
import * as deliveryController from '../controller/deliveryController.js';
import swaggerAutogen from 'swagger-autogen';
import logger from '../../logger.js';

const router = express.Router();

// Create a delivery

router.post('/agentId/:agentId/orderId/:orderId', deliveryController.createDelivery);

// Update the delivery status by orderId
router.post('/update/status/:orderId', deliveryController.updateDeliveryStatus);

// Get a specific delivery by deliveryId
router.get('/deliveryId/:deliveryId', deliveryController.getDeliveryById);

// Get deliveries by orderId
router.get('/orderId/:orderId', deliveryController.getDeliverybyOrderId);

// Get deliveries by agentId
router.get('/agentId/:agentId', deliveryController.getDeliverybyAgentId);

// Get all deliveries
router.get('/', deliveryController.getAllDeliveries);

// Delete a delivery by deliveryId
router.delete('/delete/deliveryId/:deliveryId', deliveryController.deleteDeliveryBydeliveryId);

// Delete a delivery by orderId
router.delete('/delete/orderId/:orderId', deliveryController.deleteDeliveryByOrderId);

// Delete deliveries by agentId
router.delete('/delete/deliveries/agentId/:agentId', deliveryController.deleteDeliveryByAgentId);

export default router;
