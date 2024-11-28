import axios from 'axios';
import * as orderService from '../services/orderService.js';
import mongoose from 'mongoose';
import OrderItem from '../model/OrderItem.js';
import Order from '../model/Order.js';
import logger from '../../logger.js';

export const createOrder = async (req, resp) => {
    try {
        const { customerId } = req.params;
        const { orderStatus, orderItems } = req.body;

        logger.info(`Received request to create an order for customer ID: ${customerId}`);

        // Verify customer existence
        const customerResponse = await axios.get(`http://localhost:8081/api/customer/customerId/${customerId}`);
        if (!customerResponse.data) {
            logger.error(`Customer with ID ${customerId} not found`);
            return resp.status(404).json({ message: 'Customer not found' });
        }

        logger.info(`Customer with ID ${customerId} found. Proceeding with order creation`);

        // Initialize order with a default totalPrice
        let totalAmount = 0;
        const orderData = {
            totalPrice: 0, // Will be updated later
            orderStatus,
            customer: customerId
        };

        // Save the initial order to get an order ID
        const savedOrder = await orderService.createOrder(orderData);
        logger.info(`Order created successfully with ID: ${savedOrder._id}`);

        // Process each order item, saving them and calculating total amount
        const savedOrderItems = await Promise.all(orderItems.map(async (item) => {
            const { menu, quantity } = item;
            const menuResponse = await axios.get(`http://localhost:8083/api/menu/menuId/${menu}`);

            if (!menuResponse.data) {
                logger.error(`Menu item with ID ${menu} not found`);
                throw new Error(`Menu item not found: ${menu}`);
            }

            const price = menuResponse.data.price * quantity;
            totalAmount += price;

            // Create and save OrderItem with reference to the saved order
            const orderItem = new OrderItem({
                quantity,
                price,
                menu,
                restaurant: menuResponse.data.restaurantId,
                order: savedOrder._id
            });
            return await orderItem.save();
        }));

        // Update the order with the calculated totalAmount and saved order item IDs
        savedOrder.totalPrice = totalAmount;
        savedOrder.orderItems = savedOrderItems.map(item => item._id);
        await savedOrder.save();

        // Fetch and return the populated order with item details
        const populatedOrder = await orderService.findOrderById(savedOrder._id);
        logger.info(`Order with ID ${savedOrder._id} populated with items and total price updated`);

        resp.status(201).json(populatedOrder);

    } catch (error) {
        logger.error(`Error creating order: ${error.message}`);
        resp.status(400).json({ message: error.message });
    }
};

export const getAllOrders = async (req, resp) => {
    try {
        logger.info('Received request to fetch all orders');
        const orders = await orderService.getAllOrders();
        logger.info(`Fetched ${orders.length} orders successfully`);
        resp.status(200).json(orders);
    } catch (error) {
        logger.error(`Error fetching orders: ${error.message}`);
        resp.status(500).json({ message: error.message });
    }
};

export const updateOrder = async (req, resp) => {
    const { id } = req.params;
    const updateData = req.body;
    try {
        logger.info(`Received request to update order with ID: ${id}`);
        const order = await orderService.updateOrder(id, updateData);
        if (!order) {
            logger.warn(`Order with ID ${id} not found`);
            return resp.status(404).json({ message: "Order not found" });
        }
        logger.info(`Order with ID ${id} updated successfully`);
        resp.status(200).json(order);
    } catch (error) {
        logger.error(`Error updating order with ID ${id}: ${error.message}`);
        resp.status(500).json({ message: 'Error updating order', error: error.message });
    }
};

export const findOrderById = async (req, resp) => {
    const { orderId } = req.params;
    try {
        logger.info(`Received request to find order with ID: ${orderId}`);
        const order = await orderService.findOrderById(orderId);
        if (!order) {
            logger.warn(`Order with ID ${orderId} not found`);
            return resp.status(404).json({ message: "Order not found" });
        }
        logger.info(`Found order with ID: ${orderId}`);
        resp.status(200).json(order);
    } catch (error) {
        logger.error(`Error finding order with ID ${orderId}: ${error.message}`);
        resp.status(404).json({ message: 'Error finding order', error: error.message });
    }
};

export const deleteOrder = async (req, resp) => {
    const { orderId } = req.params;
    try {
        logger.info(`Received request to delete order with ID: ${orderId}`);
        const serviceResponse = await orderService.deleteOrder(orderId);
        logger.info(`Order with ID ${orderId} deleted successfully`);
        resp.status(200).json(serviceResponse);
    } catch (error) {
        logger.error(`Error deleting order with ID ${orderId}: ${error.message}`);
        resp.status(500).json({ message: error.message });
    }
};
