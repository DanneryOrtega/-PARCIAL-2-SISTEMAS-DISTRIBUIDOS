package itp.instituto.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.entity.Student;
import itp.instituto.customer.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/students")

public class StudentRest {
    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents(@RequestParam(name = "gradeId", required = false) Long gradeId) {

        List<Student> students = new ArrayList<>();
        if (null == gradeId) {
            students = studentService.findStudentAll();
            if (students.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

        } else {
            Grade grade = new Grade();
            grade.setId(gradeId);
            students = studentService.findStudentsByGrade(grade);
            if (null == students) {
                log.error("Students with Grade id {} not found.", gradeId);
                return ResponseEntity.notFound().build();
            }
        } return ResponseEntity.ok(students);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id) {
        log.info("Fetching Student with id {}", id);
        Student student = studentService.getStudent(id);
        if (  null == student) {
            log.error("Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent( @RequestBody Student student, BindingResult result) {
        log.info("Creating Student : {}", student);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Student studentDB = studentService.createStudent (student);

        return  ResponseEntity.status( HttpStatus.CREATED).body(studentDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        log.info("Updating Student with id {}", id);

        Student currentStudent = studentService.getStudent(id);

        if ( null == currentStudent ) {
            log.error("Unable to update. Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        student.setId(id);
        currentStudent=studentService.updateStudent(student);
        return  ResponseEntity.ok(currentStudent);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Student with id {}", id);

        Student student = studentService.getStudent(id);
        if ( null == student ) {
            log.error("Unable to delete. Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(student);
        return (ResponseEntity<Void>)ResponseEntity.noContent();
    }



    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}//final

