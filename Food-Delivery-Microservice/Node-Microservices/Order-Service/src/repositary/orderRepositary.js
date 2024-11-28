// Order Repository
import Order from "../model/Order.js";
import OrderItem from "../model/OrderItem.js";

export const createOrder = async (orderData) => {
    const order = new Order(orderData);
    return await order.save();
};

export const getAllOrders = async () => {
    return await Order.find();
};

export const updateOrder = async (orderId, updateData) => {
    return await Order.findByIdAndUpdate(orderId, updateData, { new: true });
};

export const findOrderById = async (orderId) => {
    return await Order.findById(orderId);
};

export const createOrderItem = async (orderItem) => {
    const createOrderItem = new OrderItem(orderItem);
    return await createOrderItem.save();
};

export const deleteOrder = async (orderId) => {
    const order = await Order.findById(orderId);
    if (!order) return null;

    await OrderItem.deleteMany({ order: orderId });
    return await Order.findByIdAndDelete(orderId);
};
