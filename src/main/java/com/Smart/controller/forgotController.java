/*
 * package com.Smart.controller;
 * 
 * import java.security.Principal;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.Smart.Dao.UserRepository; import com.Smart.Entities.User;
 * 
 * @Controller public class forgotController {
 * 
 * @Autowired private UserRepository userRepository;
 * 
 * // forgot email form///
 * 
 * @RequestMapping("/forgot")
 * 
 * public String openEmailForm(Model model, Principal principal) {
 * 
 * if (principal != null) { String Username = principal.getName();
 * 
 * 
 * // get the user using userName(Email)
 * 
 * User user = this.userRepository.getUserByUserName(Username);
 * 
 * 
 * 
 * 
 * 
 * model.addAttribute("user", user);
 * 
 * model.addAttribute("title", "forgot password");
 * 
 * return "forgotform"; }
 * 
 * else { return "forgotform"; }
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 */