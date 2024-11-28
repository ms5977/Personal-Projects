import axios from 'axios';
export const customerResponse = async (customerId) => {
    return await axios.get(`http://localhost:8081/api/customer/customerId/${customerId}`);
}
export const restaturantResponse = async (restaurantId) => {
    return await axios.get(`http://localhost:8082/api/restaurant/rating/restaurantId/${restaurantId}`);
}
export const menuResponse = async (menuId) => {
    return await axios.get(`http://localhost:8083/api/menu/review/menuId/${menuId}`);
}
export const orderResponse = async (orderId) => {
    return await axios.get(`http://localhost:5000/api/orders/orderId/${orderId}`);
}
export const updateResturantReview = async (restaurantId, rating) => {
    return await axios.put(`http://localhost:8082/api/restaurant/update/review/restaurantId/${restaurantId}?rating=${rating}`);
}
export const updateMenuReview = async (menuId, rating) => {
    return await axios.put(`http://localhost:8083/api/menu/update/review/menuId/${menuId}?rating=${rating}`);
}