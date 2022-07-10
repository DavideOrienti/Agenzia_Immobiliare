package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.CredentialsValidator;
import it.uniroma3.siw.validator.UtenteValidator;


@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UtenteValidator utenteValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Utente());
		model.addAttribute("credentials", new Credentials());
		return "registerUser";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "loginForm";
	}



	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAfterLogin(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("credentials", credentials);
		return "home";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String defaultAfterRegistration(Model model) {

		return "index";
	}

	@RequestMapping(value = "/admin/adminPanel")
	public String getAdminPanel() {
		return "/admin/home";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("utente") Utente user,
			BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {

		// validate user and credentials fields
		this.utenteValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSuccessful";
		}
		return "registerUser";
	}
}
//@Controller
//public class AuthenticationController {
//	
//	@Autowired
//	private CredentialsService credentialsService;
//	
//	public static boolean loggato=false;
//	public static boolean admin=false;
//	
//	//public static User userCorrente;
//	
//	@Autowired
//	private UtenteValidator userValidator;
//	
//	@Autowired
//	private CredentialsValidator credentialsValidator;
//	
//	@RequestMapping(value = "/register", method = RequestMethod.GET) 
//	public String showRegisterForm (Model model) {
//		model.addAttribute("utente", new Utente());
//		model.addAttribute("credentials", new Credentials());
//		return "registerUser";
//	}
//	
//	@RequestMapping(value = "/login", method = RequestMethod.GET) 
//	public String showLoginForm (Model model) {
//		return "loginForm";
//	}
//	
//	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
//	public String logout(Model model) {
//		loggato=false;
//		admin=false;
//		model.addAttribute("login",AuthenticationController.loggato);
//		model.addAttribute("credentials",AuthenticationController.admin);
//		return "index";
//	}
//	
//    @RequestMapping(value = "/default", method = RequestMethod.GET)
//    public String defaultAfterLogin(Model model ,HttpSession httpSession) {
//    	loggato=true;
//    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//    	
//    	 httpSession.setAttribute("utente",credentials.getUser());
//    	
//    	//this.userCorrente=credentials.getUser();
//    	model.addAttribute("login",AuthenticationController.loggato);
//    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//    		admin=true;
//    	 }
//        return "index";
//    }
//	
//    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
//    public String registerUser(@ModelAttribute("utente") Utente utente,
//                 BindingResult userBindingResult,
//                 @ModelAttribute("credentials") Credentials credentials,
//                 BindingResult credentialsBindingResult,
//                 Model model) {
//
//        // validate user and credentials fields
//        this.userValidator.validate(utente, userBindingResult);
//        this.credentialsValidator.validate(credentials, credentialsBindingResult);
//
//        // if neither of them had invalid contents, store the User and the Credentials into the DB
//        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
//            // set the user and store the credentials;
//            // this also stores the User, thanks to Cascade.ALL policy
//            credentials.setUser(utente);
//            credentialsService.saveCredentials(credentials);
//            return "registrationSuccessful";
//        }
//        return "registerUser";
//    }
//    }
//   


