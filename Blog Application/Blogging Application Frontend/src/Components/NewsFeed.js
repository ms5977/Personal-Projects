import React, { useEffect, useState } from 'react'
import { deletePostService, loadAllPosts } from '../Services/PostService'
import { Col, Container, Pagination, PaginationItem, PaginationLink, Row } from 'reactstrap';
import Post from './Post';
import InfiniteScroll from 'react-infinite-scroll-component';
import { toast } from 'react-toastify';

export default function NewsFeed() {
    const inputStyle = {
        outline: 'none',
        boxShadow: 'none',
    };
    const [postcontent, setPostContent] = useState({
        content: [],
        totalPages: '',
        totalElemnts: '',
        pageSize: '',
        lastPage: false,
        pageNumber: ''
    }
    );
    const [currentPage, setCurrentPage] = useState(0);
    const changePage = (pageNumber = 0, pageSize = 5) => {
        if (pageNumber > postcontent.pageNumber && postcontent.lastPage) {
            return
        }
        if (pageNumber < postcontent.pageNumber && postcontent.pageNumber == 0) {
            return
        }
        loadAllPosts(pageNumber, pageSize).then(data => {
            setPostContent({
                content: [...postcontent.content, ...data.content],
                totalPages: data.totalPages,
                totalElemnts: data.totalElemnts,
                pageSize: data.pageSize,
                lastPage: data.lastPage,
                pageNumber: data.pageNumber
            })
            // console.log(data);

            // window.scroll(0, 0);
        }).catch(error => {
            console.log(error);

            console.log("Error is Loading");

        })
    }

    useEffect(() => {
        loadAllPosts().then((data) => {
            // console.log(currentPage);
            changePage(currentPage)
            setPostContent(data);

        }).catch(error => {
            console.log(error);

        })
    }, [currentPage])

    const changePageInfinite = () => {
        // console.log("Page Changed");
        setCurrentPage(currentPage + 1);
    }

    function deletePost(post) {
        // console.log(post);
        deletePostService(post.postId).then(resp => {
            // console.log(resp);
            toast.success("post is deleted")
            let newPostContents = postcontent.content.filter(p => p.postId != post.postId)
            setPostContent({ ...postcontent, content: newPostContents });

        }).catch(error => {
            // console.log(error);
            toast.error("error in deleting post");

        })

    }
    return (
        <div className='container-fluid'>

            <Row className="mb-4">
                <Col md={{ size: 12 }} className="text-center">
                    <hr style={{ border: '1px solid' }} />
                    <h1 className="mb-4">Welcome to Our Blog Feed</h1>
                    <p className="lead">Explore the latest posts and updates from our community. Dive in and enjoy the stories shared by our authors.</p>
                    <hr style={{ border: '1px solid' }} />
                </Col>
            </Row>
            <Row>
                <Col md={{ size: 12 }}>
                    {/* <h1 className='text-center'>Blog Count: {postcontent?.totalElemnts}</h1> */}
                    {postcontent.content.map((post) =>
                        <Post deletePost={deletePost} key={post.postId} post={post} />
                    )}

                    <InfiniteScroll
                        dataLength={postcontent.content.length}
                        next={changePageInfinite}
                        hasMore={!postcontent.lastPage}
                        loader={<h4>Loading...</h4>}


                        endMessage={
                            <p style={{ textAlign: 'center' }}>
                                <b>Yay! You have seen it all</b>
                            </p>
                        }

                    >

                    </InfiniteScroll>









                    {/* <Container className='mt-2'>
                        <Pagination size='lg' style={inputStyle}>
                            <PaginationItem onClick={() => changePage(postcontent.pageNumber - 1)} disabled={postcontent.pageNumber == 0}>
                                <PaginationLink
                                    previous
                                />
                            </PaginationItem>
                            {
                                [...Array(postcontent.totalPages)].map((item, index) => (
                                    <PaginationItem style={inputStyle} onClick={() => changePage(index)} key={index} active={index == postcontent.pageNumber}>
                                        <PaginationLink >
                                            {index + 1}
                                        </PaginationLink>
                                    </PaginationItem>
                                ))

                            }
                            <PaginationItem style={inputStyle} onClick={() => changePage(postcontent.pageNumber + 1)} disabled={postcontent.lastPage}>
                                <PaginationLink
                                    next
                                />
                            </PaginationItem>
                        </Pagination>
                    </Container> */}
                </Col>
            </Row>

        </div>
    )
}
