import express from 'express';
import * as reviewController from '../controller/ReviewController.js';

const router = express.Router();
router.post('/create', reviewController.createReview);
router.get("/", reviewController.getAllReview);
router.get('/reviewId/:reviewId', reviewController.getReviewById);
router.get('/restaurantId/:restaurantId', reviewController.getReviewByRestaurantId);
router.get('/menuId/:menuId', reviewController.getReviewByMenuId);
router.delete('/delete/:reviewId', reviewController.deleteReviewById);

export default router;