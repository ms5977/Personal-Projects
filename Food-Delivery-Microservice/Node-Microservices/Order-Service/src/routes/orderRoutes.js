import express from 'express'
import * as orderController from '../controller/orderController.js';

const router = express.Router();
router.post('/customerId/:customerId', orderController.createOrder);
router.get('/', orderController.getAllOrders);
router.put("/:id", orderController.updateOrder)
router.get("/orderId/:orderId", orderController.findOrderById);
router.delete('/delete/:orderId', orderController.deleteOrder);
export default router;