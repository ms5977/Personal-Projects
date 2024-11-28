import React, { useEffect, useRef, useState } from 'react';
import { Button, Card, CardBody, Container, Form, Input, Label } from "reactstrap"
import { loadAllCatgories } from '../Services/CategoryService';
import JoditEditor from 'jodit-react';
import { toast } from 'react-toastify'
import { createPost as doCreatePost, uploadPostImage } from '../Services/PostService';
import { getCurrentUserDetails } from '../Auth/Auth';
const AddPost = () => {
    const inputStyle = {
        outline: 'none',
        boxShadow: 'none',
        borderColor: 'grey', // Optional: Custom border color on focus
    };
    const editor = useRef(null)
    const [user, setuser] = useState(undefined);

    const [categories, setCategories] = useState([]);
    const [post, setPost] = useState({
        title: "",
        content: "",
        categoryId: ""
    })
    const [image, setImage] = useState();
    useEffect(() => {
        setuser(getCurrentUserDetails())
        loadAllCatgories().then((data) => {
            setCategories(data)
            // console.log(data);

        }).catch((error) => {
            // console.log(error);

        })
    }, [])
    // field Change Function
    const fieldChanged = (event) => {
        // console.log(event.target.value);
        setPost({ ...post, [event.target.name]: event.target.value })

    }
    const contentFieldChange = (data) => {
        setPost({ ...post, 'content': data })
    }
    const createPost = (event) => {
        event.preventDefault();
        console.log(post);

        if (post.title.trim() === '') {
            toast.error("post title is required !!")
        }

        if (post.content.trim() === '') {
            toast.error("post content is required !!")
        }

        if (post.categoryId === '') {
            toast.error("Select some Category !!")
        }
        // submit the form on server
        post['userId'] = user.id
        doCreatePost(post).then(data => {
            uploadPostImage(image, data.postId).then((data) => {
                toast.success("Image uploaded")
            }).catch((error) => {
                toast.error("Error in uploading")
                console.log(error);

            })

            toast.success(" Post Created Successfully")

            setPost({
                title: "",
                content: "",
                categoryId: ""
            })
        }).catch(error => {
            toast.error("error")
            console.log("error", error);

        })

    }
    // Image Upload
    const handleFileChange = (event) => {
        console.log(event.target.files[0]);
        setImage(event.target.files[0]);

    }


    return (
        <div className='wrapper'>
            <Card className='shadow  mt-2'>
                <CardBody className='text-center'>
                    <h3 style={{ fontWeight: 'bolder' }}>Share Your Thoughts</h3>
                </CardBody>
                <CardBody >
                    {/* {JSON.stringify(post)} */}
                    <Form onSubmit={createPost}>
                        <div className='my-3'>
                            <Label for='title'>Post Title</Label>
                            <Input
                                type='text'
                                id='title'
                                placeholder='Enter here'
                                className='rounded-0'
                                name='title'
                                style={inputStyle}
                                onChange={fieldChanged}
                            >

                            </Input>
                        </div>

                        <div className='my-3'>
                            <Label for='content'>Post Content</Label>
                            {/* <Input
                                type='textarea'
                                id='title'
                                placeholder='Enter here'
                                className='rounded-0'
                                name='title'
                                style={{ height: '300px', ...inputStyle }}
                            >

                            </Input> */}
                            <JoditEditor
                                ref={editor}
                                value={post.content}
                                onChange={(newContent) => contentFieldChange(newContent)}


                            />
                            {/* file field  */}
                            <div className="mt-3">
                                <Label for='image'>Select Post Banner</Label>
                                <Input id='image' type='file' onChange={handleFileChange} />
                            </div>
                        </div>
                        <div className='my-3'>
                            <Label for='category'>Post Category</Label>
                            <Input
                                type='select'
                                id='category'
                                placeholder='Enter here'
                                className='rounded-0'
                                name='categoryId'
                                style={inputStyle}
                                onChange={fieldChanged}
                                defaultValue={0}
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
                            <Button type='submit' color='primary' className='rounded-0'>Create Post</Button>
                            <Button type='reset' color='danger' className='rounded-0 ms-2'>Reset Content</Button>
                        </Container>
                    </Form>
                    {/* {content} */}
                </CardBody>
            </Card>
        </div>
    );
}

export default AddPost;
