import axios from "axios"
import { getToken } from "../Auth/Auth";
export const BASE_URL = "http://localhost:5000";

export const myAxios = axios.create({
    baseURL: BASE_URL
});

export const loginUser = (loginDetails) => {
    return myAxios.post("/api/auth/login", loginDetails).then((response) => response.data);
}

export const privateAxios = axios.create({
    baseURL: BASE_URL,
});

privateAxios.interceptors.request.use(
    (config) => {
        const token = getToken();
        if (token) {
            if (!config.headers) {
                config.headers = {};
            }
            config.headers.Authorization = `Bearer ${token}`;
            // console.log("config", config);
        }
        return config;
    },
    (error) => Promise.reject(error)
)
