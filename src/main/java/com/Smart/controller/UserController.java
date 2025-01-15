
package com.Smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.JPopupMenu.Separator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.Dao.ContactRepository;
import com.Smart.Dao.UserRepository;
import com.Smart.Entities.Contact;
import com.Smart.Entities.User;
import com.Smart.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	/*
	 * private static final User user = null;
	 */
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	/*
	 * @ModelAttribute public void addCommonData(Model model, Principal principal) {
	 * 
	 * 
	 * }
	 */

	// home Dashboard //

	@GetMapping("/index")
	public String dashboard(Model model, Principal principal) {
		if (principal != null) {
			String Username = principal.getName();
			System.out.println("User logged in: " + Username);

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			System.out.println("USER  " + user);

			model.addAttribute("user", user);

			model.addAttribute("title", "User-Dashboard");

			return "NORMAL/user_dashboard";
		} else {
			return "redirect:/user_dashboard";
		}
	}

	// open add conntact form handler: //

	@GetMapping("/add-contact")
	public String openAddContactForm(Model model, Principal principal) {

		model.addAttribute("title", "add-contact");

		if (principal != null) {
			String Username = principal.getName();
			System.out.println("User logged in: " + Username);

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			System.out.println("USER  " + user);

			model.addAttribute("user", user);

			// contact class entities used

			model.addAttribute("contact", new Contact());

			return "NORMAL/add_contact_form";
		} else {
			return "redirect:/add_contact_form";
		}

	}

	// processing add contact //

	@PostMapping("/process-contact")
	public String processContact(Model model, @ModelAttribute Contact contact,
			@RequestParam("imageurl") MultipartFile file, Principal principal, HttpSession session) {

		try {

			String name = principal.getName();

			User user = this.userRepository.getUserByUserName(name);

			model.addAttribute("user", user);

			// processing and uploding file

			if (file.isEmpty()) {

				// if the file is empty then try massage

				System.out.println("file is empty");

				contact.setImage("contact.ico");

			} else {

				// uplode the file in folder the name to conatct

				contact.setImage(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/img").getFile();

				/*
				 * Path path = Paths.get(saveFile.getAbsolutePath() + File.pathSeparator +
				 * file.getOriginalFilename());
				 */

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("image is uploded");

			}

			user.getContacts().add(contact);

			contact.setUser(user);

			this.userRepository.save(user);

			System.out.println("addted to database");

			System.out.println("adding contact :" + contact);

			// contact sccessfully added message :

			session.setAttribute("message", new message("Contect suceesfully added !! add more contact..", "success"));

		} catch (Exception e) {

			System.out.println("ERORR " + e.getMessage());

			e.printStackTrace();

			// contact unseccesfully added message :

			session.setAttribute("message", new message("Somethink went wrong !! please try again!", "danger"));
		}

		return "NORMAL/add_contact_form";

	}

	// show (view contact) contact handler
	// per page = 5[n]
	// cuerrent page =0[page]

	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {

		if (principal != null) {
			String Username = principal.getName();
			/* System.out.println("User logged in: " + Username); */

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			/* System.out.println("USER  "+ user ); */

			// cureent page - page
			// contact perPage-5

			Pageable pageable = PageRequest.of(page, 5);

			Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

			model.addAttribute("contacts", contacts);

			model.addAttribute("currentPage", page);
			model.addAttribute("totalPage", contacts.getTotalPages());

			model.addAttribute("user", user);

			model.addAttribute("title", "Show-Contacts");

			return "NORMAL/show_contacts";
		} else {
			return "redirect:/show_contacts";
		}

	}

	// Show Particular Contact Details.

	@RequestMapping("/contact/{cId}")
	public String showContactDetail(@PathVariable("cId") Integer cId, Principal principal, Model model) {

		if (principal != null) {
			String Username = principal.getName();
			System.out.println("User logged in: " + Username);

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			/* System.out.println("USER  "+ user ); */

			/* model.addAttribute("contact",new Contact()); */

			model.addAttribute("user", user);

			// contact class entities used

			Optional<Contact> contactoptional = this.contactRepository.findById(cId);

			Contact contact = contactoptional.get();

			// if userId == ConatctId --> show the contact Details//

			if (user.getId() == contact.getUser().getId()) {

				model.addAttribute("contact", contact);
				model.addAttribute("title", contact.getName());

			}

			System.out.println("img url " + contact.getImage());
			System.out.println("name is   " + contact.getName());

			return "NORMAL/contact_detail";
		} else {
			return "NORMAL/contact_detail";
		}

	}

	// delete contact handler//

	@GetMapping("/Delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, HttpSession session,
			Principal principal) {

		if (principal != null) {
			String username = principal.getName();
			User user = this.userRepository.getUserByUserName(username);

			model.addAttribute("user", user);
			model.addAttribute("title", "show-contacts");

			Optional<Contact> contactOptional = this.contactRepository.findById(cId);

			if (contactOptional.isPresent()) {
				Contact contact = contactOptional.get();

				// Ensure the contact belongs to the logged-in user
				if (contact.getUser().getId() == (user.getId())) {
					contact.setUser(null);
					this.contactRepository.delete(contact);
					session.setAttribute("message", new message("Contact successfully deleted!", "success"));
				} else {
					session.setAttribute("message", new message("Unauthorized deletion attempt!", "danger"));
				}
			} else {
				session.setAttribute("message", new message("Contact not found!", "danger"));
			}
			return "redirect:/user/show-contacts/0";
		} else {
			return "redirect:/user/show-contacts/0";
		}
	}

	/*
	 * // open-Update from handler//
	 * 
	 * @PostMapping("/update-contact/{cId}") public String
	 * updateForm(@PathVariable("cId") Integer cId, Model model, Principal
	 * principal) { if (principal != null) { String username = principal.getName();
	 * User user = this.userRepository.getUserByUserName(username);
	 * 
	 * model.addAttribute("user", user); model.addAttribute("title",
	 * "Update-contacts");
	 * 
	 * Contact contact = this.contactRepository.findById(cId).get();
	 * 
	 * model.addAttribute("contact", contact);
	 * 
	 * return "NORMAL/update_form"; } else { return "NORMAL/update_form"; } }
	 */

	@GetMapping("/update-contact/{cId}")
	public String updateForm(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			User user = this.userRepository.getUserByUserName(username);

			model.addAttribute("user", user);
			model.addAttribute("title", "Update Contacts");

			// Fetch the contact to update
			Contact contact = this.contactRepository.findById(cId)
					.orElseThrow(() -> new RuntimeException("Contact not found with ID: " + cId));

			model.addAttribute("contact", contact);

			return "NORMAL/update_form";
		} else {
			return "NORMAL/update_form"; // Redirect to login if principal is null
		}
	}

	// procces the update contact and save update contact //
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("imageurl") MultipartFile file,
			Principal principal, HttpSession session) {
		try {

			User user = this.userRepository.getUserByUserName(principal.getName());
			contact.setUser(user);

			Contact oldcontactDetail = this.contactRepository.findById(contact.getcId()).get();

			// Handle file upload if applicable
			if (!file.isEmpty()) {

				// delete old photos//

				try {
					// Define the path to the static folder
					Path deleteOldPhotoPath = new ClassPathResource("static/img").getFile().toPath();

					// Create a path for the file to delete
					Path fileToDelete = deleteOldPhotoPath.resolve(oldcontactDetail.getImage());

					// Check if the file exists and delete it
					if (Files.exists(fileToDelete)) {
						Files.delete(fileToDelete);
						System.out.println("File deleted successfully: " + fileToDelete);
					} else {
						System.out.println("File not found: " + fileToDelete);
					}
				} catch (Exception e) {
					// Handle any exceptions
					System.err.println("Error deleting file: " + e.getMessage());
					e.printStackTrace();
				}

				// update new photo

				File saveFile = new ClassPathResource("static/img").getFile();

				/*
				 * Path path = Paths.get(saveFile.getAbsolutePath() + File.pathSeparator +
				 * file.getOriginalFilename());
				 */

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setImage(file.getOriginalFilename());

			} else {

				contact.setImage(oldcontactDetail.getImage());
			}

			// Debugging: Log received values
			System.out.println("Contact Name: " + contact.getName());
			System.out.println("Contact ID: " + contact.getcId());

			// Save contact
			this.contactRepository.save(contact);

			session.setAttribute("message", new message("Contact updated successfully!", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new message("Something went wrong!", "danger"));
		}

		return "redirect:/user/contact/" + contact.getcId(); // Redirect to an appropriate page
	}

	// user profile page handler//

	@GetMapping("/profile")
	public String yourprofile(Model model, Principal principal) {

		if (principal != null) {
			String Username = principal.getName();

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			model.addAttribute("user", user);

			model.addAttribute("title", "profile");

			return "NORMAL/profile";
		} else {
			return "redirect:/profile";
		}
	}

	// open settings handler//

	@GetMapping("/settings")
	public String openSettings(Model model, Principal principal) {

		if (principal != null) {
			String Username = principal.getName();

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			model.addAttribute("user", user);

			model.addAttribute("title", "settings");

			return "NORMAL/settings";
		} else {
			return "redirect:/settings";
		}
	}

	// change password//

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Model model, Principal principal, HttpSession session) {

		if (principal != null) {
			String Username = principal.getName();

			// get the user using userName(Email)

			User user = this.userRepository.getUserByUserName(Username);

			User currentUser = this.userRepository.getUserByUserName(Username);

			System.out.println("current old password is :-->  " + currentUser.getPassword());

			model.addAttribute("user", user);

			model.addAttribute("title", "settings");

			System.out.println("OLD PASSWORD :" + oldPassword);
			System.out.println("NEW PASSWORD :" + newPassword);
			
			// password change  here //

			if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {

				currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
				this.userRepository.save(currentUser);

				/* message sent */

				session.setAttribute("message", new message("your password updated successfully!", "success"));

			} else {

				session.setAttribute("message", new message("Wrong password ! try again ", "success"));

				return "NORMAL/settings";
			}

		}

		else {
			return "redirect:NORMAL/user_dashboard";
		}

		return "redirect:NORMAL/user_dashboard";

	} 

	
	  // forgot email form///
	  
	@RequestMapping("/forgot")
	public String openEmailForm(Model model, Principal principal) {

	    if (principal != null) {
	        String Username = principal.getName();

	        // Get the user using userName (Email)
	        User user = this.userRepository.getUserByUserName(Username);

	        model.addAttribute("user", user);
	        model.addAttribute("title", "forgot password");

	        return "/forgotform";
	    } else {
	        return "/forgotform";
	    }
	}

	 

	// Sent OTP //
	@PostMapping("/send-otp")
	public String sentOTP(@RequestParam("email") String email, Model model, Principal principal) {
	    if (principal != null) {
	        String username = principal.getName();

	        Random random = new Random(1000);
	        int otp = random.nextInt(90000) + 10000;

	        System.out.println("Generated OTP is: " + otp);

	        User user = this.userRepository.getUserByUserName(username);
	        System.out.println("User is: " + username);

	        model.addAttribute("user", user);
	        model.addAttribute("title", "OTP verify");

	        return "NORMAL/profile";
	    } else {
	        System.out.println("Principal is null. Redirecting to OTP verification page.");
	        return "NORMAL/profile";
	    }
	}

	

	}
