import mongoose from "mongoose";
import * as reviewStatus from '../enum/ReviewStatus.js';

const ReviewSchema = new mongoose.Schema({
    order: {
        orderId: {
            type: mongoose.Schema.Types.ObjectId,
            unique: true
        },
        rating: {
            type: Number,
            min: 1,
            max: 5,
        },
        comment: {
            type: String,
            default: null
        },
        status: {
            type: String,
            enum: Object.values(reviewStatus),
        }
    },
    customerId: {
        type: Number,
        required: true
    },
    restaurant: {
        id: {
            type: Number,
        },
        name: {
            type: String,
        },
        rating: {
            type: Number,
            min: 1,
            max: 5,
        },
        comment: {
            type: String,
        },
        status: {
            type: String,
            enum: Object.values(reviewStatus),
        }
    },
    menu: {
        id: {
            type: Number,
            default: null  // Allows flexibility if not provided
        },
        name: {
            type: String,
            default: null  // Optional name field for menu item
        },
        rating: {
            type: Number,
            min: 1,
            max: 5,
            default: null  // Optional rating field for menu item
        },
        comment: {
            type: String,
            default: null
        },
        status: { // Separate status for menu
            type: String,
            enum: Object.values(reviewStatus),
            default: 'pending'
        }
    },

}, {
    timestamps: true,
    toJSON: {
        transform: function (doc, ret) {
            if (!ret.menu || Object.keys(ret.menu).every(key => ret.menu[key] == null)) {
                delete ret.menu;
            }
            if (!ret.restaurant || Object.keys(ret.restaurant).every(key => ret.restaurant[key] == null)) {
                delete ret.restaurant;
            }
            if (!ret.order || Object.keys(ret.order).every(key => ret.order[key] == null)) {
                delete ret.order;
            }
            return ret;
        }
    }

}

);
export default mongoose.model('Review', ReviewSchema);
