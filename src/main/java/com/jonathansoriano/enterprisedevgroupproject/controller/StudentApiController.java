package com.jonathansoriano.enterprisedevgroupproject.controller;

import com.jonathansoriano.enterprisedevgroupproject.domain.StudentRequest;
import com.jonathansoriano.enterprisedevgroupproject.model.Student;
import com.jonathansoriano.enterprisedevgroupproject.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API controller exposing the student search as a JSON endpoint.
 * This is used by automated tests and can be consumed directly by API clients.
 * The corresponding MVC view is served by StudentController (search.html).
 */
@RestController
@RequestMapping("/")
public class StudentApiController {

    private final StudentService service;

    public StudentApiController(StudentService service) {
        this.service = service;
    }

    /**
     * Returns a JSON list of students matching the provided search filters.
     * All parameters are optional; omitting a parameter means it will not be used
     * as a filter.
     *
     * @param firstName      optional first name filter (partial match supported)
     * @param lastName       optional last name filter (partial match supported)
     * @param city           optional city filter (partial match supported)
     * @param state          optional state filter (exact match, e.g. "OH")
     * @param universityName optional university name filter (partial match
     *                       supported)
     * @param grade          optional grade filter (exact match, e.g. "Senior")
     * @return 200 OK with the list of matching students, or 404 if none found
     */
    @GetMapping("findStudent")
    public ResponseEntity<List<Student>> find(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String grade) {
        StudentRequest request = StudentRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .residentCity(city)
                .residentState(state)
                .universityName(universityName)
                .grade(grade)
                .build();

        return ResponseEntity.ok(service.find(request));
    }
}
