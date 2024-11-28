import mongoose, { Schema } from "mongoose";
const DeliveryAgentSchema = new Schema({
    name: {
        type: String,
        required: true
    },
    mail: {
        type: String,
        required: true
    },
    phoneNumber: {
        type: Number,
        required: true
    },
    location: {
        houseNo: {
            type: String,
            required: true
        },
        area: {
            type: String,
            required: true
        },
        city: {
            type: String,
            required: true
        },
        pinCode: {
            type: String,
            required: true
        },

    },

}, { timestamps: true })
export default mongoose.model('DeliveryAgent', DeliveryAgentSchema);