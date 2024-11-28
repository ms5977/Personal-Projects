// isLoggedIn

export const isLoggedIn = () => {
    let data = localStorage.getItem("data");
    if (data != null) {
        return true;
    }
    else {
        return false;
    }
}
// doLogin=>data=>set to LocalStorage

export const doLogin = (data, next) => {
    localStorage.setItem("data", JSON.stringify(data))
    next()
}

// doLogout=>remove from localStarage

export const doLogout = (next) => {
    localStorage.removeItem("data")
    next()
}

// get Current User Logged Details

export const getCurrentUserDetails = () => {
    if (isLoggedIn()) {
        return JSON.parse(localStorage.getItem("data")).userDto;
    }
    else {
        return false;
    }
}
export const getToken = () => {
    if (isLoggedIn()) {
        return JSON.parse(localStorage.getItem("data")).token
    } else {
        return null;
    }
}
// console.log(getToken());