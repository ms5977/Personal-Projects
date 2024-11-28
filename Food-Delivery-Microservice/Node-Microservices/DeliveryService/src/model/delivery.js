import mongoose, { Schema } from "mongoose";
import * as deliveryStatus from '../enum/deliveryStatus.js'

const deliverySchema = new Schema({
    deliveryStatus: {
        type: String,
        enum: Object.values(deliveryStatus),
        required: true
    },
    order: {
        type: mongoose.Schema.Types.ObjectId,
        required: true,
        unique: true
    },
    deliveryAgent: {
        type: mongoose.Schema.Types.ObjectId,
        required: true,
    }
})
export default mongoose.model('Delivery', deliverySchema);