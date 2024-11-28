import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormGroup, Input, Label, Row } from "reactstrap";
import Base from "../Components/Base";
import { useContext, useState } from "react";
import { loginUser } from "../Services/Helper";
import { toast } from "react-toastify"
import { doLogin } from "../Auth/Auth";
import { useNavigate } from "react-router-dom";
import UserContext from "../Context/UserContext";
export function Login() {

    const userContextData = useContext(UserContext);

    const navigate = useNavigate();
    const [loginDetails, setLoginDetails] = useState({
        username: "",
        password: "",
    });
    const handleChange = (event, field) => {
        let actualValue = event.target.value;
        // console.log(actualValue);
        // console.log(field);

        setLoginDetails({
            ...loginDetails,
            [field]: actualValue
        })
    }
    const handleReset = () => {
        setLoginDetails({
            username: "",
            password: "",
        })
    }
    const handleFormSubmit = (event) => {
        event.preventDefault();
        console.log(loginDetails);
        // validation
        if (loginDetails.username.trim() == "" || loginDetails.password.trim() == "") {
            toast.error("Username or Password Required")
        }
        // submit the user data to server to genrate the token
        loginUser(loginDetails).then((data) => {
            // console.log("Token");
            doLogin(data, () => {
                // console.log("Login details save in Local Storage");
                userContextData.setUser({
                    data: data.user,
                    login: true,
                })
            });
            // console.log(data);
            navigate("/user/dashboard")
            toast.success("login Success");


        }).catch(error => {
            // console.log(error);
            // console.log(error.response.status);
            toast.error("Login Error")
            if (error.response.status == 400 || error.response.status == 404) {
                toast.error(error.response.data.messaage);
            }
            else {
                toast.error("Something went wrong on server");
            }

        })
    }

    return (

        <Base>

            <Container style={{ margin: '7% auto' }}>
                <Row className="mt-4" >
                    <Col sm={{ size: 6, offset: 3 }}  >
                        <Card color="white" shadow >
                            <CardHeader className="text-center loginPage"><h3 >Login</h3></CardHeader>
                            <CardBody className="loginPage">
                                {/* Form FIelds */}
                                <Form onSubmit={handleFormSubmit}>
                                    {/* username Field */}
                                    <FormGroup>
                                        <Label for="email">Username</Label>
                                        <Input type="email"
                                            placeholder="Enter here"
                                            id="email"
                                            value={loginDetails.username}
                                            onChange={(e) => handleChange(e, "username")}
                                        />
                                    </FormGroup>
                                    {/* Password Field */}
                                    <FormGroup>
                                        <Label for="password">Password</Label>
                                        <Input type="password"
                                            placeholder="Enter here"
                                            id="password"
                                            value={loginDetails.password}
                                            onChange={(e) => handleChange(e, "password")}
                                        />
                                    </FormGroup>
                                    <Container className="text-center">
                                        <Button color="primary">Submit</Button>
                                        <Button onClick={handleReset} className="ms-2 " color="primary" type="reset">Reset</Button>
                                    </Container>
                                </Form>
                            </CardBody>
                        </Card>
                    </Col>
                </Row>
            </Container>

        </Base>
    )
}