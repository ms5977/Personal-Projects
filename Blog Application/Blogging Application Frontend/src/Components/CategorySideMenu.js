import React, { useEffect, useState } from 'react';
import { ListGroup, ListGroupItem } from 'reactstrap';
import { loadAllCatgories } from '../Services/CategoryService';
import { Link } from 'react-router-dom';

const CategorySideMenu = () => {
    const [categoires, setCategoires] = useState([]);
    useEffect(() => {
        loadAllCatgories().then((data) => {
            console.log(data);

            setCategoires([...data])
        }).catch((error) => {
            console.log(error);

        })
    }, [])
    return (
        <div>
            <ListGroup className='border-0'>
                <ListGroupItem tag={Link} to={'/'} action={true} className='border-0'>
                    All Blogs
                </ListGroupItem>
                {
                    categoires && categoires.map((cat, index) => {
                        return (
                            <ListGroupItem tag={Link} to={'/categories/' + cat.categoryId} className='border-0 mt-2 shadow' key={index} action={true}>
                                {cat.categoryTitle}
                            </ListGroupItem>
                        )
                    })
                }
            </ListGroup>
        </div>
    );
}

export default CategorySideMenu;
