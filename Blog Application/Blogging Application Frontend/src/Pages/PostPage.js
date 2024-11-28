import React, { useEffect, useState } from 'react';
import Base from '../Components/Base';
import { Link, useParams } from 'react-router-dom';
import { Button, Card, CardBody, CardText, Col, Container, Input, Row } from 'reactstrap';
import { createComment, loadPost } from '../Services/PostService';
import { toast } from 'react-toastify';
import { BASE_URL } from '../Services/Helper';
import { isLoggedIn } from '../Auth/Auth';

const PostPage = () => {

    const { postId } = useParams();
    const [post, setPost] = useState(null);
    const [comment, setComment] = useState({
        content: ''
    });
    useEffect(() => {
        // load post of postId
        loadPost(postId).then(data => {
            // console.log(data);
            setPost(data);
        }).catch(error => {
            console.log(error);
            toast.error("Error in loading in post");

        })
    }, [postId]);
    const printDate = (number) => {
        return new Date(number).toLocaleString('en-IN', {
            day: 'numeric',
            month: 'short',
            year: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            second: 'numeric',
        });
    };
    // image styling
    const imageStyles = {
        borderRadius: '8px',
        objectFit: 'cover',
        maxHeight: '500px',
        width: '100%',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
        transition: 'transform 0.2s ease-in-out',
        textAlign: 'center',
    };
    // submit Post
    const submitPost = () => {
        if (!isLoggedIn()) {
            toast.error("Please login to comment !!!")
            return
        }
        if (comment.content.trim() == '') {
            return;
        }
        createComment(comment, post.postId).then(data => {
            console.log(data);
            toast.success("comment posted")
            setPost({
                ...post, comments: [...post.comments, data.data]
            })
            setComment({
                content: ''
            })

        }).catch(error => {
            // console.log(error);
            toast.error("Errro in Submitting the post");

        })
    }
    return (
        <Base>

            <Container className='mt-4'>
                <Link to="/">Home</Link>/{post && (<Link to={''}>{post.title}</Link>)}
                <Row>
                    <Col md={{ size: 12 }}>

                        {post ? (
                            <Card className='mt-3 ps-2'>
                                <CardBody>
                                    Posted By: <b>{post.user.name}</b> on <b>{printDate(post.addDate)}</b>
                                    <CardText><span className='text-muted'><b>Category:  </b>{post.category.categoryTitle}</span></CardText>
                                    <div className="divider" style={{ width: '100%,', width: "2px", backgroundc: 'black' }}></div>

                                    <div className='mt-2'><h3>{post.title}</h3></div>
                                    <div className="image-container mt-3">
                                        <img className='img-fluid' style={imageStyles} src={BASE_URL + '/api/post/image/' + post.imageName} alt="not found"
                                        />
                                    </div>
                                    <CardText className='mt-4' dangerouslySetInnerHTML={{ __html: post.content }}></CardText>
                                </CardBody>
                            </Card>
                        ) : (
                            <p>Loading...</p>
                        )}

                    </Col>
                </Row>

                <Row className='mt-4'>
                    <Col md={{
                        size: 9,
                        offset: 1
                    }}>

                        <h3>Comments:({post ? post.comments.length : 0})</h3>
                        {
                            post && post.comments.map((c, index) => (
                                <Card className='mt-4 border-0' key={index}>
                                    <CardBody>
                                        <CardText>{c.content}</CardText>
                                    </CardBody>
                                </Card>
                            ))
                        }
                        <Card className='mt-4 border-0'>
                            <CardBody>
                                <Input
                                    type='textarea'
                                    placeholder='Enter comment here'
                                    value={comment.content}
                                    onChange={(event) => setComment({ content: event.target.value })}
                                >
                                </Input>
                                <Button onClick={submitPost} className='mt-2' color='primary'>Submit</Button>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </Base>

    );
}

export default PostPage;
