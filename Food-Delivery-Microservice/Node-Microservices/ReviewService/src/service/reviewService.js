import * as reviewRepository from '../repository/reviewRepository.js';
import * as externalService from '../externalService/externalServices.js';
import mongoose, { Mongoose } from 'mongoose';
import logger from '../../logger.js';

// Helper function to validate external entities
const validateExternalEntity = async (externalServiceFn, id, entityName) => {
    const response = await externalServiceFn(id);
    if (!response || !response.data) {  // Check for valid response
        throw new Error(`${entityName} not found for ID: ${id}`);
    }
    return response;
};

// Function to calculate the average rating for a restaurant and update it
const calculateRestaurantRating = async (restaurantId) => {
    try {
        const restaurants = await reviewRepository.findReviewByRestaurantId(restaurantId);
        if (!restaurants || restaurants.length === 0) {
            logger.info(`No reviews found for restaurant ID: ${restaurantId}`);
            return;
        }
        // Calculate the average rating
        const ratings = restaurants.map((restaurant) => {
            // Ensure rating is a valid number
            const rating = restaurant.restaurant.rating;
            return rating && !isNaN(rating) ? rating : 0;  // Default to 0 if rating is invalid
        });
        const totalRating = (ratings.reduce((sum, rating) => sum + rating, 0));
        const averageRating = ((totalRating / ratings.length).toFixed(1));
        logger.info("Rating", ratings);
        logger.info("Rating Length", ratings.length);
        logger.info("Total Rating", totalRating);
        logger.info("Average Rating", averageRating);
        // Update the restaurant rating via external service
        await externalService.updateResturantReview(restaurantId, averageRating);

        logger.info(`Updated average rating for restaurant ID ${restaurantId}: ${averageRating}`);
    } catch (error) {
        logger.error(`Error calculating restaurant review for ID ${restaurantId}:`, error.message);
    }
};
// Function to calculate the average rating for a restaurant and update it
const calculateMenuRating = async (menuId) => {
    try {
        const menus = await reviewRepository.findReviewByMenuId(menuId);
        if (!menus || menus.length === 0) {
            logger.info(`No reviews found for menu ID: ${menuId}`);
            return;
        }
        // Calculate the average rating
        const ratings = menus.map((menu) => {
            // Ensure rating is a valid number
            const rating = menu.menu.rating;
            return rating && !isNaN(rating) ? rating : 0;  // Default to 0 if rating is invalid
        });
        const totalRating = (ratings.reduce((sum, rating) => sum + rating, 0));
        const averageRating = ((totalRating / ratings.length).toFixed(1));
        console.log("Rating", ratings);
        console.log("Rating Length", ratings.length);
        console.log("Total Rating", totalRating);
        console.log("Average Rating", averageRating);
        // Update the restaurant rating via external service
        await externalService.updateMenuReview(menuId, averageRating);
        console.log(`Updated average rating for menu ID ${menuId}: ${averageRating}`);
    } catch (error) {
        console.error(`Error calculating menu review for ID ${menuId}:`, error.message);
    }
};
export const createReview = async (reviewData) => {
    try {
        const { customerId, order, restaurant, menu, rating, comment } = reviewData;
        if (!customerId) {
            throw new Error('Missing required field: customerId is mandatory.');
        }
        await validateExternalEntity(externalService.customerResponse, customerId, 'customer');
        let restaurantName = null;
        let restaurantStatus = null;
        let restaurantRating = null;
        let menuName = null;
        let menuRating = null;
        let menuStatus = null;
        let orderStatus = null;
        let orderRating = null;
        if (order && order.orderId) {
            await validateExternalEntity(externalService.orderResponse, order.orderId, 'order');
            orderRating = order.rating || null;
            orderStatus = order.status || "pending";

        }
        if (restaurant && restaurant.id) {
            const restaurantResponse = await validateExternalEntity(externalService.restaturantResponse, restaurant.id, 'restaurant');
            restaurantName = restaurantResponse.data ? restaurantResponse.data.name : null;
            restaurantRating = restaurant.rating || null;
            restaurantStatus = restaurant.status || "pending";
        }
        // If menu is provided, validate and get its name and rating
        if (menu && menu.id) {
            const menuResponse = await validateExternalEntity(externalService.menuResponse, menu.id, 'menu');
            menuName = menuResponse.data ? menuResponse.data.itemName : null;
            menuRating = menu.rating || null;
            menuStatus = menu.status || 'pending';
        }
        // Create the review object
        const review = {
            customerId,
            order: {
                orderId: order ? order.orderId : null,
                rating: orderRating,
                comment: order ? order.comment : null,
                status: orderStatus
            },
            restaurant: {
                id: restaurant ? restaurant.id : null,
                name: restaurantName,  // Set restaurant name if available
                rating: restaurantRating,  // Set restaurant rating if available
                comment: restaurant ? restaurant.comment : null,
                status: restaurantStatus
            },
            menu: {
                id: menu ? menu.id : null,
                name: menuName,  // Set menu name if available
                rating: menuRating,  // Set menu rating if available
                comment: menu ? menu.comment : null,
                status: menuStatus
            }
        };
        const savedReview = await reviewRepository.createReview(review);
        if (restaurant && restaurant.id) {
            logger.info("Restaturant Rating");
            await calculateRestaurantRating(restaurant.id)
        }
        if (menu && menu.id) {
            logger.info("Menu Rating");
            await calculateMenuRating(menu.id);
        }
        return savedReview;

    } catch (error) {
        console.error("Error creating review:", error.message);
        throw new Error(`Failed to create review: ${error.message}`);
    }
};

export const getReviewById = async (reviewId) => {
    try {
        if (!reviewId) {
            const error = new Error("Please provide a review ID.");
            error.status = 400;
            throw error;
        }
        const review = await reviewRepository.findReviewById(reviewId);
        if (!review) {
            const error = new Error(`Resource not found with given ID: ${reviewId}`);
            error.status = 404; // Attach a status code to the error object
            throw error;
        }
        return review;
    } catch (error) {
        logger.error("Error in Review:", error);
        throw error;
    }

}
export const getReviewByMenuId = async (menuId) => {
    try {
        if (!menuId) {
            const error = new Error("Please provide a menu ID.");
            error.status = 400;
            throw error;
        }
        const menus = await reviewRepository.findReviewByMenuId(menuId);
        if (menus.length == 0) {
            const error = new Error(`No review available with corresponsding given menu id:${menuId}`)
            error.status = 404
            throw error;
        }
        if (!menus) {
            const error = new Error(`Resource not found with given ID: ${menuId}`);
            error.status = 404; // Attach a status code to the error object
            throw error;
        }
        return menus;
    } catch (error) {
        logger.error("Error in menu:", error);
        throw error;
    }
}
export const getReviewByRestaurantId = async (restaurantId) => {
    try {
        if (!restaurantId) {
            const error = new Error("Please provide a restaurant ID.");
            error.status = 400;
            throw error;
        }
        const restaurants = await reviewRepository.findReviewByRestaurantId(restaurantId);
        if (!restaurants) {
            const error = new Error(`Resource not found with given ID: ${restaurantId}`);
            error.status = 404; // Attach a status code to the error object
            throw error;
        }
        return restaurants;
    } catch (error) {
        logger.error("Error in restaurant:", error);
        throw error;
    }
};
export const deleteReviewById = async (reviewId) => {
    try {
        if (!reviewId) {
            const error = new Error("Please provide a review ID.");
            error.status = 400;
            throw error;
        }
        const review = await reviewRepository.deleteReviewById(reviewId);
        console.log(review);

        if (!review) {
            const error = new Error(`Resource not found with given ID: ${reviewId}`);
            error.status = 404; // Attach a status code to the error object
            throw error;
        }
        return review;
    } catch (error) {
        logger.error("Error in in review", error);
        throw error;
    }
}
export const getAllReview = async () => {
    try {
        const review = await reviewRepository.findAllReview();
        if (!review) {
            const error = new Error(`No resource  found :`);
            error.status = 404; // Attach a status code to the error object
            throw error;
        }
        return review;
    } catch (error) {
        logger.error("Error in in review", error);
        throw error;
    }
}



