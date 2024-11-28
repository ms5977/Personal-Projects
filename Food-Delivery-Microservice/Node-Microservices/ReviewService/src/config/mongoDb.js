import { connect } from "mongoose"

const connectDb = async () => {
    try {
        const conn = await connect(process.env.MONGO_URI, {});
        console.log(`MongoDb connect :${conn.connection.host}`);

    } catch (error) {
        console.error(`Error ${error.message}`);

    }
}
export default connectDb