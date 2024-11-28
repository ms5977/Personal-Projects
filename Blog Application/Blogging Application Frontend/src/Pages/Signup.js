import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormFeedback, FormGroup, Input, Label, Row } from "reactstrap";
import Base from "../Components/Base";
import { useState } from "react";
import { signup } from "../Services/UserService";
import { toast } from "react-toastify";


function Signup() {

    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        about: ""
    });
    const [error, setError] = useState({
        errors: {},
        isError: false,
    });


    // handle Change
    const handleChange = (e, property) => {
        // dynamic setting the values
        setData({ ...data, [property]: e.target.value })
    }
    // Reset the Data
    const resetData = () => {
        setData({
            name: "",
            email: "",
            password: "",
            about: ""
        })
    }
    // submit form
    const submitForm = (event) => {
        event.preventDefault();

        // validate data
        // console.log(data);

        // call server for api for sending data
        signup(data).then((resp) => {
            console.log(resp);
            console.log("Success log");
            toast.success("User is Registered Succesfully !! user id: " + resp.id)
            setData({
                name: "",
                email: "",
                password: "",
                about: "",
            });

        }).catch((error) => {
            // console.log(error);
            // console.log("Error Log");
            toast.error("signup-error")
            // handle error proper way
            setError({
                errors: error,
                isError: true,
            });

        });

    };

    return (

        <Base>
            <Container>
                <Row className="mt-4">
                    <Col sm={{ size: 6, offset: 3 }}>
                        <Card color="white">
                            <CardHeader className="text-center loginPage">
                                <h3>Fill the Information to Register</h3>
                            </CardHeader>
                            <CardBody className="loginPage">
                                {/* creating Form */}
                                <Form onSubmit={submitForm}>
                                    {/* Name Field */}
                                    <FormGroup>
                                        <Label for="name">Name</Label>
                                        <Input type="text"
                                            id="name"
                                            placeholder="Enter here"
                                            onChange={(e) => handleChange(e, "name")}
                                            value={data.name}
                                            invalid={
                                                error.errors?.response?.data?.name ? true : false
                                            }

                                        />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.name}
                                        </FormFeedback>
                                    </FormGroup>
                                    {/* Email Field */}
                                    <FormGroup>
                                        <Label for="email">Email</Label>
                                        <Input type="email"
                                            id="email"
                                            placeholder="Enter here"
                                            onChange={(e) => handleChange(e, "email")}
                                            value={data.email}
                                            invalid={
                                                error.errors?.response?.data?.email ? true : false
                                            }
                                        />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.email}
                                        </FormFeedback>
                                    </FormGroup>
                                    {/* Password Field */}
                                    <FormGroup>
                                        <Label for="password">Password</Label>
                                        <Input type="passoword"
                                            id="password"
                                            placeholder="Enter here"
                                            onChange={(e) => handleChange(e, "password")}
                                            value={data.password}
                                            invalid={
                                                error.errors?.response?.data?.password ? true : false
                                            }
                                        />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.password}
                                        </FormFeedback>
                                    </FormGroup>
                                    {/* About Field */}
                                    <FormGroup>
                                        <Label for="about">About</Label>
                                        <Input type="textarea"
                                            id="about"
                                            placeholder="Enter here"
                                            onChange={(e) => handleChange(e, "about")}
                                            value={data.about}
                                            invalid={
                                                error.errors?.response?.data?.about ? true : false
                                            }
                                        />
                                        <FormFeedback>
                                            {error.errors?.response?.data?.about}
                                        </FormFeedback>
                                    </FormGroup>
                                    <Container className="text-center">
                                        <Button color="primary">Submit</Button>
                                        <Button onClick={resetData} color="primary" type="reset" className="ms-2">Reset</Button>
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
export default Signup;