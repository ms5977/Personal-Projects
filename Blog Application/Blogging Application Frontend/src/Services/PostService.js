import { myAxios, privateAxios } from "./Helper"

// create post Function
export const createPost = (postData) => {
    console.log("postData", postData);
    return privateAxios.post(
        `/api/user/${postData.userId}/category/${postData.categoryId}/posts`,
        postData
    ).then((response) => response.data)

}
// load All Post
export const loadAllPosts = (pageNumber = 0, pageSize = 5) => {
    return myAxios.get(`/api/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addDate&sortDir=desc`).then(response => response.data)
}
// load postby id
export const loadPost = (postId) => {
    return myAxios.get("/api/posts/" + postId).then((response) => response.data)
}

// create Comment
export const createComment = (comment, postId) => {
    return privateAxios.post(`/api/post/${postId}/comments`, comment);
};
// upload post banner image
export const uploadPostImage = (image, postId) => {
    let formData = new FormData()
    formData.append("image", image)
    return privateAxios.post(`/api/post/image/upload/${postId}`, formData, {
        'Content-Type': 'multipart/form-data'
    }).then((response) => response.data)
};
// load Categoires by id
export const loadPostCategoryWise = (categoryId) => {
    return privateAxios.get(`/api/category/${categoryId}/posts`).then(resp => resp.data);
}
// load postby User
export function loadPostUserWise(userId) {
    return privateAxios.get(`/api/user/${userId}/posts`).then((resp) => resp.data);
}
// delete Post
export function deletePostService(postId) {
    return privateAxios.delete(`/api/posts/${postId}`).then((resp) => resp.data);
}

export function doUpdatePost(post, postId) {
    console.log(postId);
    return privateAxios.put(`/api/posts/${postId}`, post).then((resp) => resp.data)

}