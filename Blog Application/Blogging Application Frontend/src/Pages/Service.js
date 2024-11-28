import Base from "../Components/Base";
import UserContext from "../Context/UserContext";

export function Service(params) {
    return (

        <UserContext>
            {
                (user) => (

                    <Base>
                        <h1>Services"{user.name}</h1>
                    </Base>
                )
            }

        </UserContext>
    )
}