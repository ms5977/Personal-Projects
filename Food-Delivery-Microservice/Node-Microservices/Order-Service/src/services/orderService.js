// Order Service
import * as orderRepository from '../repositary/orderRepositary.js';
import * as externalService from '../externalService/externalServices.js';
import logger from '../../logger.js';
export const createOrder = async (orderData) => {
    try {
        return await orderRepository.createOrder(orderData);
    } catch (error) {
        logger.error("Error creating order:", error.message);
        throw new Error("Failed to create order");
    }
};

export const getAllOrders = async () => {
    try {
        const orders = await orderRepository.getAllOrders();
        if (!orders || orders.length === 0) {
            throw new Error("No orders found");
        }
        return orders;
    } catch (error) {
        logger.error("Error fetching orders:", error.message);
        throw new Error("Failed to fetch orders");
    }
};

export const updateOrder = async (orderId, updateData) => {
    try {
        const updatedOrder = await orderRepository.updateOrder(orderId, updateData);
        if (!updatedOrder) {
            throw new Error(`Order with ID ${orderId} not found`);
        }
        return updatedOrder;
    } catch (error) {
        logger.error("Error updating order:", error.message);
        throw new Error("Failed to update order");
    }
};

export const findOrderById = async (orderId) => {
    try {
        const order = await orderRepository.findOrderById(orderId);
        if (!order) {
            throw new Error(`Order with ID ${orderId} not found`);
        }
        return order;
    } catch (error) {
        logger.error("Error finding order:", error.message);
        throw new Error("Failed to fetch order");
    }
};

export const createOrderItem = async (orderItem) => {
    try {
        return await orderRepository.createOrderItem(orderItem);
    } catch (error) {
        logger.error("Error creating order item:", error.message);
        throw new Error("Failed to create order item");
    }
};

export const deleteOrder = async (orderId) => {
    try {
        const deletedOrder = await orderRepository.deleteOrder(orderId);
        if (!deletedOrder) {
            throw new Error(`Order with ID ${orderId} not found in the orders system`);
        }

        const response = await externalService.deletedeliveryByOrderId(orderId);

        if (!response || !response.data) {
            return {
                message: `Order with ID ${orderId} deleted successfully, but no corresponding delivery entry was found.`,
                externalServiceResponse: null
            };
        }
        return {
            message: "Order deleted successfully",
            externalServiceResponse: response?.data || "Delivery deletion successful"
        };
    } catch (error) {
        logger.error("Error deleting order:", error.message);
        if (error.message.includes('Request failed with status code 404')) {
            return {
                message: `Order with ID ${orderId} was deleted, but no delivery entry found.`,
                externalServiceResponse: null
            };
        }
        throw new Error("Failed to delete order: " + error.message);
    }
};

