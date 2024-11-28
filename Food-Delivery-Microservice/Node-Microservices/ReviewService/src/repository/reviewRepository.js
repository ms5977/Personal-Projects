import Review from '../model/Review.js'
export const createReview = async (reviewData) => {
    const review = new Review(reviewData);
    return await review.save();
}
export const findReviewById = async (reviewId) => {
    return await Review.findById(reviewId);
}
export const findReviewByMenuId = async (menuId) => {
    return await Review.find({ 'menu.id': menuId });
}
export const findReviewByRestaurantId = async (restaurantId) => {
    return await Review.find({ 'restaurant.id': restaurantId });
};
export const deleteReviewById = async (reviewId) => {
    const review = await Review.findByIdAndDelete(reviewId);
    console.log(review);
    return review;

}
export const findAllReview = async () => {
    return await Review.find({});
}
