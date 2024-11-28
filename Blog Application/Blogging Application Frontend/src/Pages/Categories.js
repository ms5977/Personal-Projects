import React, { useEffect, useState } from 'react';
import Base from '../Components/Base';
import { useParams } from 'react-router-dom';
import CategorySideMenu from '../Components/CategorySideMenu';
import { Col, Container, Row } from 'reactstrap';
import { deletePostService, loadPostCategoryWise } from '../Services/PostService';
import Post from '../Components/Post';
import { toast } from 'react-toastify';

const Categories = () => {
    const { categoryId } = useParams();
    const [posts, setPosts] = useState([]);
    useEffect(() => {
        // console.log(categoryId);
        loadPostCategoryWise(categoryId).then((data) => {
            setPosts([...data])
        }).catch((error) => {
            toast.error("Catgories Error")
            // console.log(error);

        })

    }, [categoryId])

    function deletePost(post) {
        console.log(post);
        deletePostService(post.postId).then(resp => {
            // console.log(resp);
            toast.success("post is deleted")
            let newPosts = posts.filter(p => p.postId != post.postId)
            setPosts([...newPosts]);

        }).catch(error => {
            // console.log(error);
            toast.error("error in deleting post");

        })

    }


    return (
        <Base>
            <Container className="mt-3">
                <Row>
                    <Col md={2} className="pt-3">
                        <CategorySideMenu />
                    </Col>
                    <Col md={10}>
                        {/* <h1>Blog Count:{posts.length} </h1> */}
                        <hr style={{ border: '1px solid' }} />
                        <h1 className="mb-4 text-center">Welcome to Our Blog Feed</h1>
                        <p className="lead text-center">Explore the latest posts and updates from our community. Dive in and enjoy the stories shared by our authors.</p>
                        <hr style={{ border: '1px solid' }} />
                        {
                            posts && posts.map((posts, index) => {
                                return (
                                    <Post deletePost={deletePost} key={index} post={posts}></Post>
                                )
                            })


                        }

                        <div className='text-center' style={{ margin: '18% auto' }}>
                            {posts.length <= 0 ? <h2 >No Post in this Category !! 😔😔😔</h2> : ''}
                        </div>
                    </Col>
                </Row>
            </Container>
        </Base>
    );
}

export default Categories;
