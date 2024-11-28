import React, { useState, useEffect, useContext } from 'react';
import { NavLink as ReactLink, useNavigate } from 'react-router-dom';
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  NavbarText,
} from 'reactstrap';
import { doLogout, getCurrentUserDetails, isLoggedIn } from '../Auth/Auth';
import UserContext from '../Context/UserContext';

function CustomNavbar(args) {
  const userContextData = useContext(UserContext)
  const [isOpen, setIsOpen] = useState(false);
  let navigate = useNavigate();
  const [login, setLogin] = useState(false);
  const [user, setUser] = useState(undefined);

  const toggle = () => setIsOpen(!isOpen);

  useEffect(() => {
    setLogin(isLoggedIn());
    setUser(getCurrentUserDetails());

  }, [login]);

  const logout = () => {
    doLogout(() => {
      // logged out
      setLogin(false);
      userContextData.setUser({
        data: null,
        login: false
      })
      navigate("/")
    })
  }

  return (
    <div>
      <Navbar color='white' expand="md" className='px-5 ccc'>
        <NavbarBrand className='text-white' tag={ReactLink} to="/homePage">MyBlog</NavbarBrand>
        <NavbarToggler onClick={toggle} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="me-auto" navbar >
            <NavItem>
              <NavLink className='text-white' tag={ReactLink} to="/homePage">Home</NavLink>
            </NavItem>
            <NavItem>
              <NavLink className='text-white' tag={ReactLink} to="/">Blog Feed</NavLink>
            </NavItem>
            {/* <NavItem>
              <NavLink tag={ReactLink} to="/about">About</NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactLink} to="/services">Services</NavLink>
            </NavItem> */}

            {/* <UncontrolledDropdown nav inNavbar>
              <DropdownToggle nav caret >
                More
              </DropdownToggle>
              <DropdownMenu >
                <DropdownItem tag={ReactLink} to="/services">Contact Us</DropdownItem>
                <DropdownItem>Facebook</DropdownItem>
                <DropdownItem>Youtube</DropdownItem>
                <DropdownItem>Instagram</DropdownItem>
                <DropdownItem>Linkedin</DropdownItem>
              </DropdownMenu>
            </UncontrolledDropdown> */}
          </Nav>

          <Nav navbar>
            {
              login && (
                <>
                  <NavItem>
                    <NavLink className='text-white' tag={ReactLink} to={`/user/profileInfo/${user.id}`}>
                      Profile
                    </NavLink>
                  </NavItem>
                  <NavItem>
                    <NavLink className='text-white' tag={ReactLink} to="/user/dashboard">
                      {user.email}
                    </NavLink>
                  </NavItem>
                  <NavItem>
                    <NavLink className='text-white' type='button' onClick={logout}>
                      Logout
                    </NavLink>
                  </NavItem>
                </>
              )
            }
            {
              !login && (

                <>
                  <NavItem>
                    <NavLink className='text-white' tag={ReactLink} to="/login">
                      Login
                    </NavLink>
                  </NavItem>
                  <NavItem>
                    <NavLink className='text-white' tag={ReactLink} to="/signup">
                      Signup
                    </NavLink>
                  </NavItem>

                </>
              )
            }

          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
}

export default CustomNavbar;