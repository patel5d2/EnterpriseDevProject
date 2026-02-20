package com.jonathansoriano.enterprisedevgroupproject.controller;

import com.jonathansoriano.enterprisedevgroupproject.domain.StudentRequest;
import com.jonathansoriano.enterprisedevgroupproject.exception.SearchNotFoundException;
import com.jonathansoriano.enterprisedevgroupproject.model.Student;
import com.jonathansoriano.enterprisedevgroupproject.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * MVC Controller exposing the student search page and handling search form
 * submissions.
 * Requires authentication (enforced by SecurityConfig).
 */
@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Renders the student search page. Executes a search if any filter parameters
     * are provided; otherwise renders the empty search form.
     *
     * @param firstName      optional first name filter
     * @param lastName       optional last name filter
     * @param city           optional city filter
     * @param state          optional state filter (e.g., "OH", "KY")
     * @param universityName optional university name filter
     * @param grade          optional grade/year filter
     * @param model          Spring MVC model for passing data to the template
     * @return the search Thymeleaf template name
     */
    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String grade,
            Model model) {

        // If at least one filter was provided, execute the search
        boolean hasFilter = isNotBlank(firstName) || isNotBlank(lastName) || isNotBlank(city)
                || isNotBlank(state) || isNotBlank(universityName) || isNotBlank(grade);

        if (hasFilter) {
            StudentRequest request = StudentRequest.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .residentCity(city)
                    .residentState(state)
                    .universityName(universityName)
                    .grade(grade)
                    .build();

            try {
                List<Student> students = service.find(request);
                model.addAttribute("students", students);
                model.addAttribute("resultCount", students.size());
            } catch (SearchNotFoundException e) {
                model.addAttribute("noResults", true);
            }

            // Echo back the search params for form persistence
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("city", city);
            model.addAttribute("state", state);
            model.addAttribute("universityName", universityName);
            model.addAttribute("grade", grade);
        }

        return "search";
    }

    /**
     * Utility method to check if a string is non-null and non-blank.
     *
     * @param s the string to check
     * @return true if the string has content
     */
    private boolean isNotBlank(String s) {
        return s != null && !s.isBlank();
    }
}
