import { Col, Container, Row } from "reactstrap";
import Base from "../Components/Base";
import NewsFeed from "../Components/NewsFeed";
import CategorySideMenu from "../Components/CategorySideMenu";

export function Home() {
    return (
        <Base>
            <Container className="mt-3">
                <Row>
                    <Col md={2} className="pt-3">
                        <CategorySideMenu />
                    </Col>
                    <Col md={10}>
                        <NewsFeed />
                    </Col>
                </Row>
            </Container>
            {/* <h1>Home</h1> */}

        </Base>
    )
}