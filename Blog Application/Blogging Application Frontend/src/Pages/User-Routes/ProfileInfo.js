import React, { useContext, useEffect, useState } from 'react';
import Base from '../../Components/Base';
import UserContext from '../../Context/UserContext';
import { useParams } from 'react-router-dom';
import { getUser } from '../../Services/UserService';
import { Col, Row } from 'reactstrap';
import ViewUserProfile from '../../Components/ViewUserProfile';

const ProfileInfo = () => {
    const object = useContext(UserContext);
    const { userId } = useParams();
    const [user, setUser] = useState();

    useEffect(() => {
        getUser(userId).then(data => {
            console.log(data);
            setUser({ ...data })
        })
    }, [])

    const userView = () => {
        return (
            <Row>
                <Col md={{ size: 6, offset: 3 }}>
                    <ViewUserProfile user={user} />
                </Col>
            </Row>
        )
    }
    return (
        <Base>
            <div>
                {user ? userView() : 'Loading user Data'}
            </div>
        </Base>

    );
}

export default ProfileInfo;
