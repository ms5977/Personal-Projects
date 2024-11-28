import Delivery from "../model/delivery.js"
export const createDelivery = async (deliveryData) => {
    const delivery = new Delivery(deliveryData);
    return await delivery.save();
}
export const findDeliveryById = async (deliveryId) => {
    return await Delivery.findById(deliveryId);
}
export const findDeliverybyOrderId = async (orderId) => {
    return await Delivery.findOne({ order: orderId })
}
export const findDeliverybyAgentId = async (agentId) => {
    return await Delivery.find({ deliveryAgent: agentId })
}
export const findAllDeliveries = async () => {
    return await Delivery.find();
}
export const updateDeliveryStatusByOrderId = async (orderId, deliveryData) => {
    return await Delivery.findOneAndUpdate({ order: orderId }, { $set: deliveryData }, { new: true });
}
export const deleteDeliveryBydeliveryId = async (deliveryId) => {
    return await Delivery.findByIdAndDelete(deliveryId);
}
export const deleteDeliveryByOrderId = async (orderId) => {
    return await Delivery.findOneAndDelete({ order: orderId });
}
export const deleteDeliveryByAgentId = async (agentId) => {
    return await Delivery.deleteMany({ deliveryAgent: agentId });
}