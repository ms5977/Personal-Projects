import React from 'react';
import Base from '../Components/Base';
import styles from './HomePage.module.css'

const HomePage = () => {
    // const homePageStyle = {
    //     backgroundImage: `url(/homeImage.jpg)`, // Path relative to the public folder
    //     backgroundSize: 'cover',
    //     backgroundPosition: 'center',
    //     height: '100vh',
    //     display: 'flex',
    //     justifyContent: 'center',
    //     alignItems: 'center',
    //     color: 'white',
    //     textAlign: 'center',
    //     position: 'relative',
    //     overflow: 'hidden',
    // };

    const overlayStyle = {
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        background: 'rgba(0, 0, 0, 0.5)', // dark overlay for better text readability
        zIndex: 1,
    };

    const contentStyle = {
        position: 'relative',
        zIndex: 2,
        padding: '20px',
        background: 'rgba(0, 0, 0, 0.5)', // background for the text content
        borderRadius: '10px',
        boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.6)',
    };

    const headingStyle = {
        fontSize: '3rem',
        margin: 0,
        fontWeight: 'bold',
        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.7)',
    };

    const paragraphStyle = {
        fontSize: '1.25rem',
        margin: '10px 0 0',
        fontWeight: '300',
    };

    return (
        <Base>
            <div className={styles.homePageStyle}>
                <div style={overlayStyle}></div>
                <div style={contentStyle}>
                    <h1 style={headingStyle}>Discover the art of storytelling.</h1>
                    <p style={paragraphStyle}>Step into a space where passion meets purpose. Explore our blog and join a community of thinkers and dreamers.</p>
                </div>
            </div>
        </Base>
    );
}

export default HomePage;
