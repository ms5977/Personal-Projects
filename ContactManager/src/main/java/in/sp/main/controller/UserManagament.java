package in.sp.main.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entity.ContactDetails;
import in.sp.main.entity.User;
import in.sp.main.service.UserService;
import in.sp.main.urls.OtherUrls;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserManagament {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/UserHome")
	public String openUserHome()
	{
		return "userManagement";
	}
	@GetMapping("/userProfile")
	public String openUserProfile(HttpSession session,Model model)
	{
		User user = (User) session.getAttribute("session_user");
		System.out.println(user);
		model.addAttribute("session_profile", user);
		return "user-profile";
	}
	@GetMapping("/addContact")
	public String openAddContactPage(Model model)
	{
		model.addAttribute("contact", new ContactDetails());
		return "add-contact";
	}
	@PostMapping("/addContactDetails")
	public String addContact(@ModelAttribute("contact")ContactDetails contactDetails,
							@RequestParam("contactImage")MultipartFile conatctImageFile,
							Model model,
							HttpSession session
							)
	{
		if(contactDetails.getName().isEmpty()||contactDetails.getNickName().isEmpty()||contactDetails.getExperience().isEmpty()||contactDetails.getEmail().isEmpty()||contactDetails.getPhoneno().isEmpty())
		{
			model.addAttribute("model_error", "Please fill all the Details");
		}
		else {
			
			User user = (User) session.getAttribute("session_user");
			contactDetails.setUser(user);
			
			boolean contactAdded = userService.addContactDetails(contactDetails);
			if (contactAdded) {
				model.addAttribute("model_success", "Contact Added Successfully");
			}
			else 
			{
				model.addAttribute("model_error", "Contact Not Added Due to Some Error");
			}
			if (!conatctImageFile.isEmpty()) {
				saveImage(conatctImageFile);
				System.out.println("Image Uploaded");
			}
		}
		
		contactDetails.setName("");
		contactDetails.setNickName("");
		contactDetails.setEmail("");
		contactDetails.setPhoneno("");
		contactDetails.setExperience("");
		return "add-contact";
	}
	
	 public boolean saveImage(MultipartFile file)
	 {
		 boolean status=false;
		 try 
		 {
			String fileName=file.getOriginalFilename();
			Path filePath = Paths.get(OtherUrls.IMAGE_UPLOAD_FOLDER,fileName);
			Files.copy(file.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
			status=true;
		 } 
		 catch (Exception e)
		 {
			 status=false;
			 e.printStackTrace();
		 }
		 return status;
	 }
//	 -------------------------------
	 int PAGE_SIZE=3;
	 @GetMapping("/showContact")
	 public String openShowConatct(Model model,HttpSession session,@RequestParam(defaultValue = "1")int page)
	 {
		 User user = (User) session.getAttribute("session_user");
		 if (user!=null) {
			 List<ContactDetails> contactDetailsList = userService.getContactDetailsByUserId(user.getId());
			
			 int TOTAL_CONTACT=contactDetailsList.size();
			 System.out.println("Total Contact"+TOTAL_CONTACT);
			 
			 int TOTAL_PAGES=(int) Math.ceil((double)TOTAL_CONTACT/PAGE_SIZE);
			 System.out.println("Total Pages"+TOTAL_PAGES);
			
			 int  LIST_START_INDEX=(page-1)*PAGE_SIZE;
			 System.out.println("List Start Index"+LIST_START_INDEX);
			 
			 int LIST_END_INDEX=Math.min(LIST_START_INDEX+PAGE_SIZE, TOTAL_CONTACT);
			 System.out.println("List End Index"+LIST_END_INDEX);
			 List<ContactDetails>newListContact=contactDetailsList.subList(LIST_START_INDEX, LIST_END_INDEX);
			 
//			 model.addAttribute("model_new_list_contact", newListContact);
			 model.addAttribute("model_total_pages", TOTAL_PAGES);
			 model.addAttribute("model_current_page", page);
			 
			 
			 model.addAttribute("contactDetails",newListContact);
			 
		}
		 else {
			return "redirect:/login";
		}
		 
		 return "show-contact";
	 }
	 
	 @GetMapping("/savedContactDetails")
	 public String openSavedContactDetails(@RequestParam("email")String email,Model model)
	 {
		 ContactDetails contactDetailsByEmail = userService.getContactDetailsByEmail(email);
		 model.addAttribute("contactDetailsOfSavedUser", contactDetailsByEmail);
//		 System.out.println(contactDetailsByEmail.getName());
		 return "saved-Contact-Details";
	 }
	 
//	 ------------------------------
	@GetMapping("/editContact")
	public String openEditContactPage(@RequestParam("id")int id,Model model)
	{
		ContactDetails contactDetails = userService.authenticate(id);
		model.addAttribute("modelContactAtrr", new ContactDetails());
		model.addAttribute("model_contact", contactDetails);
		return "edit-contact";
	}

	@PostMapping("/updateContactDetails")
	public String updateDetails(@ModelAttribute("modelContactAtrr")ContactDetails contactDetails,Model model)
	{
		if(contactDetails.getName().isEmpty()||contactDetails.getNickName().isEmpty()||contactDetails.getExperience().isEmpty()||contactDetails.getEmail().isEmpty()||contactDetails.getPhoneno().isEmpty())
		{
			model.addAttribute("model_error", "Please fill all the Details");
		}
		else 
		{
			boolean status = userService.updateContact(contactDetails);
			if (status) {
				model.addAttribute("model_success", "Contact Updated Successfully");
			}
			else 
			{
				model.addAttribute("model_error", "Contact Not Updated Due to Some Error");
			}
		}
		return "edit-contact";
	}
	
//	-----------------
	@GetMapping("/deleteContact")
	public String deleteContact(@RequestParam("id")int id,RedirectAttributes redirectAttributes)
	{
		System.out.println(id);
		boolean status = userService.deleteEmployeeService(id);
		if (status) {
			redirectAttributes.addAttribute("model_success", "Contact Deleted Successfully");
		}
		else {
			redirectAttributes.addAttribute("model_error", "Contact not deleted due to some error");
		}
		
		return "redirect:/showContact";
	}
	
}
