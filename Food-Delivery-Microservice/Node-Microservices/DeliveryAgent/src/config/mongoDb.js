import { connect } from "mongoose"

const connectDb = async () => {
    try {
        const conn = await connect(process.env.MONGO_URI, {});
        console.log(`MongoDb Connect: ${conn.connection.host}`);
    } catch (error) {
        console.error(`Error:${error.message}`);
    }
}
export default connectDb;