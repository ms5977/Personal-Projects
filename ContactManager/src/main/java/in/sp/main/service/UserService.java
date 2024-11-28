package in.sp.main.service;

import java.util.List;

import in.sp.main.entity.ContactDetails;
import in.sp.main.entity.User;

public interface UserService 
{
	public boolean addUser(User user);
	public User authenticate(String email);
	public boolean addContactDetails(ContactDetails contactDetails);
	public List<ContactDetails>getContactDetailsByUserId(int userId);
	public ContactDetails getContactDetailsByEmail(String email);
	public ContactDetails authenticate(int id);
	public boolean updateContact(ContactDetails contactDetails);
	public boolean deleteEmployeeService(int id);
}
