import * as deliveryRepository from '../repository/deliveryRepository.js';
import logger from '../../logger.js';

export const createDelivery = async (deliveryData) => {
    try {
        return await deliveryRepository.createDelivery(deliveryData);
    } catch (error) {
        logger.error(`Error in creating delivery: ${error}`);
        throw error;
    }
};

export const getDeliveryById = async (deliveryId) => {
    try {
        const delivery = await deliveryRepository.findDeliveryById(deliveryId);
        if (!delivery) {
            throw new Error("Delivery not found");
        }
        return delivery;
    } catch (error) {
        logger.error(`Error in finding the delivery with Id:${deliveryId} - ${error}`);
        throw new Error("Failed to fetch delivery");
    }
};

export const getDeliverybyOrderId = async (orderId) => {
    try {
        const order = await deliveryRepository.findDeliverybyOrderId(orderId);
        if (!order) {
            throw new Error("Order not found");
        }
        return order;
    } catch (error) {
        logger.error(`Error in finding the order with id :${orderId} - ${error}`);
        throw new Error('Failed to Fetch order');
    }
};

export const getDeliverybyAgentId = async (agentId) => {
    try {
        const deliveries = await deliveryRepository.findDeliverybyAgentId(agentId);
        if (!deliveries || deliveries.length === 0) {
            throw new Error("Agent not found");
        }
        return deliveries;
    } catch (error) {
        logger.error(`Error in finding deliveries for the agent with id :${agentId} - ${error}`);
        throw new Error('Failed to fetch deliveries for agent');
    }
};

export const getAllDeliveries = async () => {
    try {
        const deliveries = await deliveryRepository.findAllDeliveries();
        if (!deliveries || deliveries.length === 0) {
            throw new Error("Deliveries not found");
        }
        return deliveries;
    } catch (error) {
        logger.error("Error in finding deliveries - " + error);
        throw error;
    }
};

export const updateDeliveryStatus = async (orderId, deliveryData) => {
    try {
        const delivery = await deliveryRepository.updateDeliveryStatusByOrderId(orderId, deliveryData);
        if (!delivery) {
            throw new Error("Delivery not found with order id");
        }
        return delivery;
    } catch (error) {
        logger.error("Error in updating delivery status for orderId: " + orderId + " - " + error);
        throw new Error("Failed to update delivery status");
    }
};

export const deleteDeliveryBydeliveryId = async (deliveryId) => {
    try {
        const delivery = await deliveryRepository.deleteDeliveryBydeliveryId(deliveryId);
        if (!delivery) {
            throw new Error(`Delivery not found with deliveryId: ${deliveryId}`);
        }
        return delivery;
    } catch (error) {
        logger.error("Error in deleting delivery with deliveryId: " + deliveryId + " - " + error);
        throw new Error("Failed to delete delivery");
    }
};

export const deleteDeliveryByOrderId = async (orderId) => {
    try {
        const delivery = await deliveryRepository.deleteDeliveryByOrderId(orderId);
        if (!delivery) {
            throw new Error(`Delivery not found with orderId: ${orderId}`);
        }
        return delivery;
    } catch (error) {
        logger.error("Error in deleting delivery with orderId: " + orderId + " - " + error);
        throw new Error("Failed to delete delivery with orderId");
    }
};

export const deleteDeliveryByAgentId = async (agentId) => {
    try {
        const result = await deliveryRepository.deleteDeliveryByAgentId(agentId);
        if (!result) {
            throw new Error(`Delivery not found with agentId: ${agentId}`);
        }
    } catch (error) {
        logger.error("Error in deleting delivery with agentId: " + agentId + " - " + error);
        throw error;
    }
};
