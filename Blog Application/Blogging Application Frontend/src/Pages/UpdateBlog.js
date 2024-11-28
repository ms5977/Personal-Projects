// import React, { } from 'react';
import React, { useEffect, useRef, useState, useContext } from 'react';
import { Button, Card, CardBody, Container, Form, Input, Label } from "reactstrap"
import { loadAllCatgories } from '../Services/CategoryService';
import JoditEditor from 'jodit-react';
import { toast } from 'react-toastify'
import { useNavigate, useParams } from 'react-router-dom';
import UserContext from '../Context/UserContext';
import Base from '../Components/Base';
import { doUpdatePost, loadPost } from '../Services/PostService';

const UpdateBlog = () => {
    const inputStyle = {
        outline: 'none',
        boxShadow: 'none',
        borderColor: 'grey', // Optional: Custom border color on focus
    };

    const editor = useRef(null)
    const { blogId } = useParams()
    // console.log("Blog-Id", blogId);

    const [categories, setCategories] = useState([]);
    const [post, setPost] = useState(null)
    const object = useContext(UserContext)
    const navigate = useNavigate();
    useEffect(() => {
        loadAllCatgories().then((data) => {
            // console.log("loadCategories", data);
            setCategories(data)

        }).catch(error => {
            console.log(error);

        })
        // load blog from database
        loadPost(blogId).then(data => {
            // console.log("Load Post", data);
            setPost({ ...data, categoryId: data.category.categoryId })
        }).catch(error => {
            console.log(error);
            toast.error("error is loading")

        })
    }, [])

    useEffect(() => {
        // console.log("first");
        // console.log("userEffect", post);

        if (post) {
            if (post.user.id != object.user.data.id) {
                toast.error("This is not your post")
                navigate("/")
            }
        }

    }, [post])

    const handleChange = (event, fieldName) => {
        setPost({
            ...post,
            [fieldName]: event.target.value
        })
    }
    const updatePost = (event) => {
        event.preventDefault();
        console.log("event", post, "\n");

        // console.log("Update post", { ...post });
        // console.log("categoires");
        // console.log("Category", { category: { categoryId: post.categoryId } });



        // doUpdatePost({ ...post }, post.postId).then(resp => {
        doUpdatePost({ ...post, category: { categoryId: post.categoryId } }, post.postId).then(resp => {
            toast.success("post updated")

        }).catch(error => {
            console.log(error);
            toast.error("Error in udpating post")

        })

    }


    const updateHtml = () => {
        return (
            <div className='wrapper'>
                <Card className='shadow  mt-2'>
                    <CardBody className='text-center'>
                        <h3 style={{ fontWeight: 'bolder' }}>Share Your Thoughts</h3>
                    </CardBody>
                    <CardBody >
                        {/* {JSON.stringify(post)} */}
                        <Form onSubmit={updatePost}>
                            <div className='my-3'>
                                <Label for='title'>Post Title</Label>
                                <Input
                                    type='text'
                                    id='title'
                                    placeholder='Enter here'
                                    className='rounded-0'
                                    name='title'
                                    style={inputStyle}
                                    value={post.title}
                                    onChange={(event) => handleChange(event, 'title')}
                                >

                                </Input>
                            </div>

                            <div className='my-3'>
                                <Label for='content'>Post Content</Label>
                                <JoditEditor
                                    ref={editor}
                                    value={post.content}
                                    onChange={(newContent) => setPost({ ...post, content: newContent })}


                                />
                                {/* file field  */}
                                {/* <div className="mt-3">
                                    <Label for='image'>Select Post Banner</Label>
                                    <Input id='image' type='file' onChange={''} />
                                </div> */}
                            </div>
                            <div className='my-3'>
                                <Label for='category'>Post Category</Label>
                                <Input
                                    type='select'
                                    id='category'
                                    placeholder='Enter here'
                                    className='rounded-0'
                                    name='categoryId'
                                    // style={inputStyle}
                                    value={post.categoryId}
                                    onChange={(event) => handleChange(event, 'categoryId')}
                                >
                                    <option disabled value={0}>--Select Category--</option>
                                    {
                                        categories.map((category) => (
                                            <option value={category.categoryId} key={category.categoryId}>
                                                {category.categoryTitle}
                                            </option>
                                        ))
                                    }
                                </Input>
                            </div>
                            <Container className='text-center'>
                                <Button type='submit' color='primary' className='rounded-0'>Update Post</Button>
                                <Button type='reset' color='danger' className='rounded-0 ms-2'>Reset Content</Button>
                            </Container>
                        </Form>
                        {/* {content} */}
                    </CardBody>
                </Card>
            </div>
        )
    }

    return (
        <Base>
            <Container>
                {post && updateHtml()}
            </Container>
        </Base>
    )


}




export default UpdateBlog;
