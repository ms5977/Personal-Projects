package in.sp.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.sp.main.entity.ContactDetails;
import in.sp.main.entity.User;
import in.sp.main.repositary.ContactDetailsRepostiory;
import in.sp.main.repositary.UserRepository;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactDetailsRepostiory contactDetailsRepostiory;
	
	@Override
	public boolean addUser(User user) {
		boolean status=false;
		try 
		{
			userRepository.save(user);
			status=true;
			
		} 
		catch (Exception e)
		{
			status =false;
			e.printStackTrace();
		}
		
		return status;
	}
//-------
	@Override
	public User authenticate(String email)
	{
		User user = userRepository.findByEmailIgnoreCase(email);
		
		return user;
	}
	@Override
	public boolean addContactDetails(ContactDetails contactDetails) {
		boolean status=false;
		try 
		{
			contactDetailsRepostiory.save(contactDetails);
			status=true;
		} 
		catch (Exception e) 
		{
			status=false;
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public List<ContactDetails> getContactDetailsByUserId(int userId) {

		return contactDetailsRepostiory.findByUserId(userId);
	}
	@Override
	public ContactDetails getContactDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		return contactDetailsRepostiory.findByEmail(email);
	}
	@Override
	public ContactDetails authenticate(int id) {
			ContactDetails existingEmployee = contactDetailsRepostiory.findById(id);
			return existingEmployee;
	}
	@Override
	public boolean updateContact(ContactDetails contactDetails) {
		boolean status=false;
		try {
			ContactDetails existingContactDetails = contactDetailsRepostiory.findByEmail(contactDetails.getEmail());
			if (existingContactDetails!=null) {
				
				existingContactDetails.setName(contactDetails.getName());
				existingContactDetails.setNickName(contactDetails.getNickName());
				existingContactDetails.setPhoneno(contactDetails.getPhoneno());
				existingContactDetails.setExperience(contactDetails.getExperience());
				
				contactDetailsRepostiory.save(existingContactDetails);
				status=true;
			}
		} catch (Exception e) {
			status=false;
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean deleteEmployeeService(int id) {
		boolean status=false;
		try {
			contactDetailsRepostiory.deleteById(id);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
			status=false;
		}
		return status;
	}
	
	

}

















