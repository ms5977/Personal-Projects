import { myAxios } from "./Helper"

export const loadAllCatgories = () => {
    return myAxios.get("/api/categories/").then((response) => {
        return response.data;
    });
}