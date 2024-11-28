import Base from "../Components/Base";
import UserContext from "../Context/UserContext";

export function About() {
    return (

        <UserContext.Consumer>
            {(object) => (
                <Base>
                    <h1>this is about page</h1>
                    <p>we are building blog website</p>
                    {/* {console.log(object)} */}
                    {/* <h1>Welcome user: {object.user.login && object.user.data.name}</h1> */}
                </Base>
            )}
        </UserContext.Consumer>
    )
}