import DeliveryAgent from '../model/DeliveryAgent.js'
export const createDeliveryAgent = async (deliveryAgent) => {
    return await new DeliveryAgent(deliveryAgent).save();
};

export const findAllAgents = async () => {
    return await DeliveryAgent.find();
};

export const findAgentById = async (agentId) => {
    return await DeliveryAgent.findById(agentId);
};
export const updateAgentById = async (agentId, updateData) => {
    return await DeliveryAgent.findByIdAndUpdate(agentId, updateData, { new: true });
}
export const deleteAgentById = async (agentId) => {
    return await DeliveryAgent.findByIdAndDelete(agentId);
}