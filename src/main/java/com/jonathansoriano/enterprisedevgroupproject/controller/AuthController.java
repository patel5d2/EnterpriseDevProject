package com.jonathansoriano.enterprisedevgroupproject.controller;

import com.jonathansoriano.enterprisedevgroupproject.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

/**
 * Controller for authentication-related pages: login, registration, and the
 * landing page.
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Redirects the root URL to the login page.
     *
     * @return redirect to the login route
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    /**
     * Renders the login page with SIGN IN / SIGN UP tab support.
     *
     * @param error  present if login failed
     * @param logout present if user just logged out
     * @param tab    active tab: "signin" or "signup"
     * @param model  Spring MVC model
     * @return the login template name
     */
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            @RequestParam(defaultValue = "signin") String tab,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid email or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been successfully logged out.");
        }
        model.addAttribute("activeTab", tab);
        model.addAttribute("registerForm", new RegisterForm());
        return "login";
    }

    /**
     * Handles the registration (sign-up) form submission.
     * Validates input, checks .edu email domain, then creates the account.
     *
     * @param form   the bound registration form
     * @param result binding result for validation errors
     * @param model  Spring MVC model for re-rendering the form with errors
     * @return redirect to login on success, or re-render login page on error
     */
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult result,
            Model model) {

        // Validate .edu domain
        if (!result.hasFieldErrors("email") && !form.getEmail().toLowerCase().endsWith(".edu")) {
            result.rejectValue("email", "email.domain", "Email must be a valid .edu university address.");
        }

        if (result.hasErrors()) {
            model.addAttribute("activeTab", "signup");
            return "login";
        }

        try {
            userService.register(form.getEmail(), form.getPassword());
        } catch (IllegalArgumentException e) {
            model.addAttribute("registerError", e.getMessage());
            model.addAttribute("activeTab", "signup");
            return "login";
        }

        return "redirect:/login?tab=signin&registered=true";
    }

    /**
     * Simple DTO for capturing registration form input.
     */
    @Data
    public static class RegisterForm {

        @NotBlank(message = "Email is required.")
        @Email(message = "Please enter a valid email address.")
        private String email;

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters.")
        private String password;
    }
}
