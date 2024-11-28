//External Services File
import axios from 'axios';
export const deletedeliveryByOrderId = async (orderId) => {
    console.log("External api calling");

    const response = await axios.delete(`http://localhost:5200/api/delivery/delete/orderId/${orderId}`)
    return response;
}