import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button } from 'reactstrap';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Home } from './Pages/Home';
import { Login } from './Pages/Login';
import Signup from './Pages/Signup';
import { About } from './Pages/About';
import Base from './Components/Base';
import { Service } from './Pages/Service';
import { ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import PrivateRoute from './Components/PrivateRoute';
import UserDashboard from './Pages/User-Routes/UserDashboard';
import ProfileInfo from './Pages/User-Routes/ProfileInfo';
import PostPage from './Pages/PostPage';
import UserProvider from './Context/UserProvider';
import Categories from './Pages/Categories';
import UpdateBlog from './Pages/UpdateBlog';
import HomePage from './Pages/HomePage';
function App() {
  return (
    <UserProvider>
      <div className="App">
        {/* <Base /> */}
        <BrowserRouter>
          <ToastContainer position='bottom-center'
            icon={false} // This will remove the tick icon

          />
          <Routes>
            <Route path='/homePage' element={<HomePage />} />
            <Route path='/' element={<Home />} />
            <Route path='Login' element={<Login />} />
            <Route path='signup' element={<Signup />} />
            <Route path='about' element={<About />} />
            <Route path='services' element={<Service />} />
            <Route path='/posts/:postId' element={<PostPage />} />
            <Route path='/categories/:categoryId' element={<Categories />} />
            <Route path='/user' element={<PrivateRoute />}>
              <Route path='dashboard' element={<UserDashboard />} />
              <Route path='profileInfo/:userId' element={<ProfileInfo />} />
              <Route path='update-blog/:blogId' element={<UpdateBlog />}></Route>
            </Route>

          </Routes>

        </BrowserRouter>
      </div>
    </UserProvider>
  );
}

export default App;
