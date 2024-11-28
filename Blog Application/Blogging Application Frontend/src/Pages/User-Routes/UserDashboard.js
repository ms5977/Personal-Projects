import React, { useEffect, useState } from 'react';
import Base from '../../Components/Base';
import AddPost from '../../Components/AddPost';
import { Container } from 'reactstrap';
import { getCurrentUserDetails } from '../../Auth/Auth';
import Post from '../../Components/Post';
import { deletePostService, loadPostUserWise } from '../../Services/PostService';
import { toast } from 'react-toastify';

const UserDashboard = () => {
    const [user, setUser] = useState({});
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        // console.log(getCurrentUserDetails());
        setUser(getCurrentUserDetails())
        loadPostData();
        // loa

    }, [])
    function loadPostData() {
        loadPostUserWise(getCurrentUserDetails().id).then(data => {
            // console.log(data);
            setPosts([...data])

        }).catch(error => {
            // console.log(error);
            toast.error("error in loading user posts")

        })
    }
    // function to delete post
    function deletePost(post) {
        // console.log(post);
        deletePostService(post.postId).then(resp => {
            console.log(resp);
            toast.success("post is deleted")
            let newPost = posts.filter(p => p.postId != post.postId)
            setPosts([...newPost]);

        }).catch(error => {
            // console.log(error);
            toast.error("error in deleting post");

        })

    }
    return (
        <Base>
            <Container>
                <AddPost />
                <h1 className='my-3 text-center'>Your Shared Insights</h1>
                {/* {posts.length} */}
                {
                    posts.map((post, index) => {
                        return (
                            <Post post={post} key={index} deletePost={deletePost}></Post>
                        )
                    })
                }
            </Container>
        </Base>

    );
}

export default UserDashboard;
