import logger from '../../logger.js';
import * as reviewService from '../service/reviewService.js';

export const createReview = async (req, resp) => {
    try {
        logger.info('Received request to create a review');
        const review = await reviewService.createReview(req.body);
        logger.info('Review created successfully');
        return resp.status(200).json(review);
    } catch (error) {
        logger.error(`Error creating review: ${error.message}`);
        return resp.status(500).json({ message: error.message });
    }
};

export const getReviewById = async (req, resp, next) => {
    try {
        logger.info(`Received request to get review by ID: ${req.params.reviewId}`);
        const review = await reviewService.getReviewById(req.params.reviewId);
        if (!review) {
            logger.warn(`Review with ID ${req.params.reviewId} not found`);
            return resp.status(404).json({ message: 'Review not found' });
        }
        logger.info(`Review with ID ${req.params.reviewId} found`);
        return resp.status(200).json(review);
    } catch (error) {
        logger.error(`Error fetching review with ID ${req.params.reviewId}: ${error.message}`);
        next(error);
    }
};

export const getReviewByRestaurantId = async (req, resp, next) => {
    try {
        logger.info(`Received request to get reviews for restaurant ID: ${req.params.restaurantId}`);
        const reviews = await reviewService.getReviewByRestaurantId(req.params.restaurantId);
        logger.info(`Fetched ${reviews.length} reviews for restaurant ID: ${req.params.restaurantId}`);
        return resp.status(200).json(reviews);
    } catch (error) {
        logger.error(`Error fetching reviews for restaurant ID ${req.params.restaurantId}: ${error.message}`);
        next(error);
    }
};

export const getReviewByMenuId = async (req, resp, next) => {
    try {
        logger.info(`Received request to get reviews for menu ID: ${req.params.menuId}`);
        const reviews = await reviewService.getReviewByMenuId(req.params.menuId);
        logger.info(`Fetched ${reviews.length} reviews for menu ID: ${req.params.menuId}`);
        return resp.status(200).json(reviews);
    } catch (error) {
        logger.error(`Error fetching reviews for menu ID ${req.params.menuId}: ${error.message}`);
        next(error);
    }
};

export const deleteReviewById = async (req, resp, next) => {
    try {
        logger.info(`Received request to delete review with ID: ${req.params.reviewId}`);
        await reviewService.deleteReviewById(req.params.reviewId);
        logger.info(`Review with ID ${req.params.reviewId} deleted successfully`);
        return resp.status(200).json({ message: 'Review deleted successfully' });
    } catch (error) {
        logger.error(`Error deleting review with ID ${req.params.reviewId}: ${error.message}`);
        next(error);
    }
};

export const getAllReview = async (req, resp, next) => {
    try {
        logger.info('Received request to fetch all reviews');
        const reviews = await reviewService.getAllReview();
        logger.info(`Fetched ${reviews.length} reviews`);
        return resp.status(200).json(reviews);
    } catch (error) {
        logger.error(`Error fetching all reviews: ${error.message}`);
        next(error);
    }
};
