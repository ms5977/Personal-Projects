import CustomNavbar from "./CustomNavbar"

const Base = ({ title = 'Welcome to our website', children }) => {
    return (
        <div>
            <CustomNavbar />
            {children}
        </div>
    )
}
export default Base;
