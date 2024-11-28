import mongoose from 'mongoose';
import * as OrderStatus from '../enum/OrderStatus.js';
const { Schema } = mongoose;

const OrderSchema = new Schema({
    totalPrice: {
        type: Number,
    },
    orderStatus: {
        type: String,
        enum: Object.values(OrderStatus)
    },
    orderItems: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'OrderItem'
    }],
    customer: {
        type: Number,
        ref: 'Customer',
        required: true
    },



}, { timestamps: true })
export default mongoose.model('Order', OrderSchema)

