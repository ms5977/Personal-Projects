import logger from '../../logger.js';
import * as deliveryAgentService from '../services/deliveryAgentService.js';
import axios from 'axios';

export const createDeliveryAgent = async (req, resp) => {
    try {
        logger.info('Received request to create a new delivery agent');
        const agent = await deliveryAgentService.createDeliveryAgent(req.body);
        logger.info(`Delivery agent created successfully with ID: ${agent._id}`);
        return resp.status(200).json(agent);
    } catch (error) {
        logger.error(`Error creating delivery agent: ${error.message}`);
        return resp.status(500).json({
            message: 'Failed to create delivery agent',
            error: error.message
        });
    }
};

export const getAllAgents = async (req, resp) => {
    try {
        logger.info('Received request to fetch all delivery agents');
        const agents = await deliveryAgentService.getAllAgents();
        logger.info(`Fetched ${agents.length} delivery agents successfully`);
        return resp.status(200).json(agents);
    } catch (error) {
        logger.error(`Error fetching delivery agents: ${error.message}`);
        return resp.status(500).json({ message: error.message });
    }
};

export const getAgentById = async (req, resp) => {
    try {
        logger.info(`Received request to fetch agent with ID: ${req.params.agentId}`);
        const agent = await deliveryAgentService.getAgentById(req.params.agentId);
        logger.info(`Found delivery agent with ID: ${req.params.agentId}`);
        return resp.status(200).json(agent);
    } catch (error) {
        logger.error(`Error fetching agent with ID ${req.params.agentId}: ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const updateAgentById = async (req, resp) => {
    try {
        logger.info(`Received request to update agent with ID: ${req.params.agentId}`);
        const updateAgent = await deliveryAgentService.updateAgentById(req.params.agentId, req.body);
        logger.info(`Successfully updated delivery agent with ID: ${req.params.agentId}`);
        return resp.status(200).json(updateAgent);
    } catch (error) {
        logger.error(`Error updating agent with ID ${req.params.agentId}: ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};

export const deleteAgentById = async (req, resp) => {
    try {
        logger.info(`Received request to delete agent with ID: ${req.params.agentId}`);
        const deleteAgent = await deliveryAgentService.deleteAgentById(req.params.agentId);
        logger.info(`Successfully deleted delivery agent with ID: ${req.params.agentId}`);
        return resp.status(200).json(deleteAgent);
    } catch (error) {
        logger.error(`Error deleting agent with ID ${req.params.agentId}: ${error.message}`);
        return resp.status(404).json({ message: error.message });
    }
};
