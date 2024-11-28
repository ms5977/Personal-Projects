package in.sp.main.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import in.sp.main.entity.User;
import in.sp.main.service.UserService;
import in.sp.main.urls.OtherUrls;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"/","homePage"})
	public String openHomePage()
	{
		return "home";
	}
//	@GetMapping("/homePage")
//	public String 
	
//	----------------------------------------------------------------
	@GetMapping("/reg")
	public String OpenRegisterPage(Model model)
	{
		model.addAttribute("user", new User());
		return "register-page";
	}
	
//	-----------------------------------------------------------------
	@PostMapping("/regForm")
	public String addUser(
							@ModelAttribute("user")User user,
							@RequestParam("userImage")MultipartFile userImageFile,
							Model model
						 )
	
	{
				if(user.getName().isEmpty()||user.getEmail().isEmpty()||user.getGender().isEmpty()||user.getPassword().isEmpty()||
					user.getUserImage().isEmpty()	
				 )
				{
			        model.addAttribute("model_error", "Please fill out all the required fields.");
			        return "register-page";
				}
				
				boolean userAdded = userService.addUser(user);
				if (userAdded) 
				{
					model.addAttribute("model_success", "User Added Successfully");
				}
				else
				{
					model.addAttribute("model_error", "User Not Added due Some error");
				}
				if (!userImageFile.isEmpty()) 
				{
					saveImage(userImageFile);
					System.out.println("Image Uploaded");
				}
				user.setName("");
				user.setEmail("");
				user.setGender("");
				user.setPassword("");
				user.setPhoneno("");
				user.setCity("");
		return "register-page";
	}
	
//	-----------------------------------------------------------------

	public boolean saveImage(MultipartFile file)
	{
		boolean status=false;
		try 
		{
			String fileName=file.getOriginalFilename();
			System.out.println(fileName);
			
			Path filePath = Paths.get(OtherUrls.IMAGE_UPLOAD_FOLDER,fileName);
			Files.copy(file.getInputStream(), filePath,StandardCopyOption.REPLACE_EXISTING);
			status=true;
		} 
		catch (Exception e) {
			status=false;
			e.printStackTrace();
		}
		return status;
	}
//	-----------------------------------------------------------------

	
//-----------------------Login Module---------------------------------
	
	@GetMapping("/loginPage")
	public String openLoginPage()
	{
		return "login";
	}
	
	
	@PostMapping("/loginForm")
	public String loginForm(
							@RequestParam("email1")String myemail,
							@RequestParam("pass1")String mypass,
							HttpSession session,
							Model model
							
							)
	{
		String page="login";
		User user = userService.authenticate(myemail);
		if (user!=null &&user.getPassword().equals(mypass))
		{
			session.setAttribute("session_user", user);
			page="userManagement";
		}
		else {
			model.addAttribute("model_error", "Email and Password did'nt Matched");
			page="login";
		}
		return page;
	}
//	---------------Logout-------------
	
	@GetMapping(value =  {"/exit","logout"})
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "login";
	}

	
}














