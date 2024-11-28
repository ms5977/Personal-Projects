// External Services
import axios from 'axios'
export const deleteDeliveriesByAgentId = async (agentId) => {
    const response = await axios.delete(`http://localhost:5200/api/delivery/delete/deliveries/agentId/${agentId}`);
    return response;
}