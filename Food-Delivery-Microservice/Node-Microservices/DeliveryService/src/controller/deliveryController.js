import * as deliveryService from '../services/deliveryServices.js';
import axios from 'axios';
import logger from '../../logger.js';

export const createDelivery = async (req, resp) => {
    try {
        const { agentId, orderId } = req.params;
        const { deliveryStatus } = req.body;

        logger.info(`Received request to create delivery for agentId: ${agentId}, orderId: ${orderId}`);

        const deliveryAgentResponse = await axios.get(`http://localhost:5100/api/agent/agentId/${agentId}`);
        const orderResponse = await axios.get(`http://localhost:5000/api/orders/orderId/${orderId}`);

        if (!deliveryAgentResponse) {
            logger.error(`Delivery agent not found for agentId: ${agentId}`);
            return resp.status(404).json({ message: 'Delivery Agent not found' });
        }
        if (!orderResponse) {
            logger.error(`Order not found for orderId: ${orderId}`);
            return resp.status(404).json({ message: 'Order not found' });
        }

        const deliveryData = {
            deliveryAgent: agentId,
            order: orderId,
            deliveryStatus
        };

        const savedDelivery = await deliveryService.createDelivery(deliveryData);
        logger.info(`Delivery created successfully for orderId: ${orderId}, agentId: ${agentId}`);
        return resp.status(200).json(savedDelivery);
    } catch (error) {
        logger.error(`Error in creating delivery: ${error.message}`);
        if (error.code === 11000 || (error.message && error.message.includes("E11000"))) {
            return resp.status(400).json({ message: 'Order already assigned to a delivery' });
        }
        if (error.response && error.response.status == 404) {
            return resp.status(404).json({ message: 'Service not reachable or not found' });
        }
        return resp.status(500).json({ message: 'Failed to create delivery' });
    }
};

export const getDeliveryById = async (req, resp) => {
    try {
        logger.info(`Received request to fetch delivery with deliveryId: ${req.params.deliveryId}`);
        const delivery = await deliveryService.getDeliveryById(req.params.deliveryId);
        return resp.status(200).json(delivery);
    } catch (error) {
        logger.error(`Error fetching delivery by deliveryId: ${req.params.deliveryId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const getDeliverybyOrderId = async (req, resp) => {
    try {
        logger.info(`Received request to fetch deliveries for orderId: ${req.params.orderId}`);
        const order = await deliveryService.getDeliverybyOrderId(req.params.orderId);
        return resp.status(200).json(order);
    } catch (error) {
        logger.error(`Error fetching delivery by orderId: ${req.params.orderId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const getDeliverybyAgentId = async (req, resp) => {
    try {
        logger.info(`Received request to fetch deliveries for agentId: ${req.params.agentId}`);
        const deliveries = await deliveryService.getDeliverybyAgentId(req.params.agentId);
        return resp.status(200).json(deliveries);
    } catch (error) {
        logger.error(`Error fetching deliveries by agentId: ${req.params.agentId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const getAllDeliveries = async (req, resp) => {
    try {
        logger.info('Received request to fetch all deliveries');
        const deliveries = await deliveryService.getAllDeliveries();
        return resp.status(200).json(deliveries);
    } catch (error) {
        logger.error(`Error fetching all deliveries: ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const updateDeliveryStatus = async (req, resp) => {
    try {
        const { orderId } = req.params;
        const { deliveryStatus } = req.body;
        logger.info(`Received request to update delivery status for orderId: ${orderId}`);

        const updateDelivery = await deliveryService.updateDeliveryStatus(orderId, { deliveryStatus });
        logger.info(`Delivery status updated for orderId: ${orderId}`);
        return resp.status(200).json(updateDelivery);
    } catch (error) {
        logger.error(`Error updating delivery status for orderId: ${req.params.orderId}, ${error.message}`);
        return resp.status(500).json({ message: error.message });
    }
};

export const deleteDeliveryBydeliveryId = async (req, resp) => {
    try {
        logger.info(`Received request to delete delivery with deliveryId: ${req.params.deliveryId}`);
        await deliveryService.deleteDeliveryBydeliveryId(req.params.deliveryId);
        logger.info(`Delivery deleted successfully for deliveryId: ${req.params.deliveryId}`);
        return resp.status(200).json({ message: "Delivery deleted successfully" });
    } catch (error) {
        logger.error(`Error deleting delivery by deliveryId: ${req.params.deliveryId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const deleteDeliveryByOrderId = async (req, resp) => {
    try {
        logger.info(`Received request to delete delivery with orderId: ${req.params.orderId}`);
        await deliveryService.deleteDeliveryByOrderId(req.params.orderId);
        logger.info(`Delivery deleted successfully for orderId: ${req.params.orderId}`);
        return resp.status(200).json({ message: "Delivery deleted successfully" });
    } catch (error) {
        logger.error(`Error deleting delivery by orderId: ${req.params.orderId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const deleteDeliveryByAgentId = async (req, resp) => {
    try {
        logger.info(`Received request to delete deliveries for agentId: ${req.params.agentId}`);
        await deliveryService.deleteDeliveryByAgentId(req.params.agentId);
        logger.info(`Deliveries deleted successfully for agentId: ${req.params.agentId}`);
        return resp.status(200).json({ message: "Delivery deleted successfully corresponding to agentId:" });
    } catch (error) {
        logger.error(`Error deleting deliveries by agentId: ${req.params.agentId}, ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};
