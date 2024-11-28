import mongoose, { Schema } from "mongoose";

const OrderItemSchema = new Schema({
    quantity: {
        type: Number,
        required: true
    },
    price: {
        type: mongoose.Schema.Types.Decimal128,
        required: true,
    },
    order: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Order',
        // required: true
    },
    menu: {
        type: Number,
        ref: 'Menu',
    },
    restaurant: {
        type: Number,
        ref: 'Restaurant',
        // required: true
    }
});
const OrderItem = mongoose.model('OrderItem', OrderItemSchema);
export default OrderItem;