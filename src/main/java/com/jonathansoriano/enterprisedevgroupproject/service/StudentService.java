package com.jonathansoriano.enterprisedevgroupproject.service;

import com.jonathansoriano.enterprisedevgroupproject.domain.StudentRequest;
import com.jonathansoriano.enterprisedevgroupproject.domain.StudentSignupRequest;
import com.jonathansoriano.enterprisedevgroupproject.domain.UserRequest;
import com.jonathansoriano.enterprisedevgroupproject.dto.StudentDto;
import com.jonathansoriano.enterprisedevgroupproject.exception.SearchNotFoundException;
import com.jonathansoriano.enterprisedevgroupproject.model.Student;
import com.jonathansoriano.enterprisedevgroupproject.repository.StudentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository repository){
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // We instantiate it here once in the constructor, so we don't have to instantiate it every time we use it inside this class.
    }

    public List<Student> find(StudentRequest request){
        List<Student> students = buildStudentListFromDtoList(repository.find(request));

        if(CollectionUtils.isEmpty(students)){
            throw new SearchNotFoundException("Student Not found!");
        }
        return students;

    }

    public String insertNewStudent(StudentSignupRequest student){
        //Logic to Hash the plan password before inserting into the app_user Table
        String hashedPassword = hashPlainTextPassword(student.getPassword());
        //Create a new UserRequest object from the StudentSignupRequest object to be able to insert into the app_user Table
        UserRequest userRequest = buildUserRequestFromStudentSignupRequest(student, hashedPassword);
        //Logic to add a new user(student user) to the User Table (after testing try figuring out how to use a CompletableFuture for async performance)
        int userInsertionResult = repository.insertNewUser(userRequest);


        //Logic to add a new student to the Student Table
        int studentInsertionResult = repository.insertNewStudent(student);

        if (studentInsertionResult == 0 || userInsertionResult == 0){
            //Temporary Exception Handling, need to create Custom Exception for when Student Creation fails
            throw new RuntimeException("Student Signup Failed!");
        }
        return "Student Signup Successful!";
    }

    private String hashPlainTextPassword(String password){
        return passwordEncoder.encode(password);
    }

    static UserRequest buildUserRequestFromStudentSignupRequest(StudentSignupRequest studentSignupRequest, String hashedPassword){
        return UserRequest.builder()
                .role("USER")
                .email(studentSignupRequest.getEmail())
                .password(hashedPassword)
                .build();
    }

    static List<Student> buildStudentListFromDtoList(List<StudentDto> dtoList){
        List<Student> studentList = new ArrayList<>();

        for (StudentDto dto : dtoList){
            studentList.add(buildStudentFromDto(dto));
        }
        return studentList;
    }

    static Student buildStudentFromDto(StudentDto dto){
        return Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .residentCity(dto.getResidentCity())
                .residentState(dto.getResidentState())
                .universityName(dto.getUniversityName())
                .grade(dto.getGrade())
                .major(dto.getMajor())
                .email(dto.getEmail())
                .socialMediaLink(dto.getSocialMediaLink())
                .build();
    }
}
