import { myAxios } from "./Helper"

export const signup = (user) => {
    return myAxios.post("/api/auth/register", user)
        .then((response) => response.data);
}

export const getUser = (userId) => {
    return myAxios.get(`/api/users/${userId}`).then((resp) => resp.data)
}