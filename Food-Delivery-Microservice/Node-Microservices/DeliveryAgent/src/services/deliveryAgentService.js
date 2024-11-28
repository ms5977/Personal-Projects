import * as deliveryAgentRepository from '../repositary/DeliveryAgentRepository.js'
import * as externalService from '../externalService/externalServices.js'
import logger from '../../logger.js';
export const createDeliveryAgent = async (deliveryAgent) => {
    try {
        return await deliveryAgentRepository.createDeliveryAgent(deliveryAgent);
    } catch (error) {
        logger.error('Error in createDeliveryAgent service:', error);
        throw new Error('Failed to create delivery agent');
    }
}
export const getAllAgents = async () => {
    try {
        return await deliveryAgentRepository.findAllAgents();
    } catch (error) {
        logger.error("Error in getAllAgents service", error);
        throw new Error('Failed to Fetch delivery Agents');

    }
}
export const getAgentById = async (agentId) => {
    try {
        const agent = await deliveryAgentRepository.findAgentById(agentId);
        if (!agent) {
            throw new Error("Delivery agent not found");
        }
        return agent;
    } catch (error) {
        logger.error(`Error in getAgentById service for ID :${agentId}`, error);
        throw new Error("Failed to fetch delivery Agent");

    }
}
export const updateAgentById = async (agentId, updateData) => {
    try {
        const agent = await deliveryAgentRepository.updateAgentById(agentId, updateData);
        if (!agent) {
            throw new Error("Delivery agent not found");
        }
        return agent;
    } catch (error) {
        logger.error(`Error updating delivery agent with Id: ${agentId}`);
        throw new Error("Failed to update delivery agent");
    }
}
export const deleteAgentById = async (agentId) => {
    try {
        const agent = await deliveryAgentRepository.deleteAgentById(agentId);
        if (!agent) {
            throw new Error("Delivery agent not found");
        }
        const agentResponse = await externalService.deleteDeliveriesByAgentId(agentId);
        return {
            message: "Agent deleted successfully",
            externalServiceResponse: agentResponse?.data || "Delivery deletion successful for corresponsding agentId"
        };
        // return agent;
    } catch (error) {
        logger.error(`Error deleting delivery agent :${agentId}`);
        throw new Error("Failed to delete delivery agent");
    }
}