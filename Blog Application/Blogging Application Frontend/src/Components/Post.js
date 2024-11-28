import React, { useContext, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button, Card, CardBody, CardText } from 'reactstrap'
import { getCurrentUserDetails, isLoggedIn } from '../Auth/Auth';
import UserContext from '../Context/UserContext';

export default function Post({ post = { id: -1, title: "This default post " }, deletePost }) {
    const userContextData = useContext(UserContext);
    const [user, setUser] = useState(null);
    const [login, setLogin] = useState(null);
    useEffect(() => {
        setUser(getCurrentUserDetails())
        setLogin(isLoggedIn())
    }, []);

    return (
        <Card className='border=0 shadow mt-3'>
            <CardBody >
                <h3>{post.title}</h3>

                <CardText dangerouslySetInnerHTML={{ __html: post.content.substring(0, 70) + "...." }}>

                </CardText>
            </CardBody>
            <div style={{ margin: '25px' }}>
                <Link className='btn btn-warning' to={'/posts/' + post.postId}>Read More</Link >
                {userContextData.user.login && (user && user.id === post.user.id ? <Button onClick={(event) => deletePost(post)} color='danger' className='ms-2'>Delete</Button> : '')}
                {userContextData.user.login && (user && user.id === post.user.id ? <Button tag={Link} to={`/user/update-blog/${post.postId}`} color='warning' className='ms-2'>Update</Button> : '')}
            </div>
        </Card>
    )
}
