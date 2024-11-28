import express from 'express';
import * as deliveryAgentController from '../controller/deliveryAgentController.js'

const router = express.Router();
router.post('/', deliveryAgentController.createDeliveryAgent);
router.post("/update/:agentId", deliveryAgentController.updateAgentById);
router.get("/", deliveryAgentController.getAllAgents);
router.get('/agentId/:agentId', deliveryAgentController.getAgentById)
router.delete('/delete/:agentId', deliveryAgentController.deleteAgentById);

export default router;