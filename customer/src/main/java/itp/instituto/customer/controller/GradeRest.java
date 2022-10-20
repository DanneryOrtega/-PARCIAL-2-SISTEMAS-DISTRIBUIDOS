package itp.instituto.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.service.GradeService;
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
@RequestMapping("/grades")

public class GradeRest {
    @Autowired
    GradeService gradeService;

    @GetMapping
    public ResponseEntity<List<Grade>> listAllGrades(@RequestParam(name="studentId", required = false) Long studentId){
        List<Grade> grades = new ArrayList<>();
        if (null == studentId){
            grades = gradeService.findGradeAll();
            if (grades.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        } else {
            if (null == grades){
                log.error("Grades with Student id {} not found.", studentId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(grades);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Grade> getGrade(@PathVariable("id") long id) {
        log.info("Fetching Grade with id {}", id);
        Grade grade = gradeService.getGrade(id);
        if (null == grade){
            log.error("Grade with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(grade);
    }


    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade, BindingResult result){
        log.info("Creating Grade: {}", grade);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Grade gradeDB = gradeService.createGrade(grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable("id") long id, @RequestBody Grade grade){
        log.info("Updating Grade with id{}", id);
        Grade currentGrade = gradeService.getGrade(id);
        if (null == currentGrade){
            log.error("Unable to update. Grade with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        grade.setId(id);
        currentGrade=gradeService.updateGrade(grade);
        return ResponseEntity.ok(currentGrade);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable("id") long id){
        log.info("Fetching & Deleting Grade with id {}", id);

        Grade grade = gradeService.getGrade(id);
        if (null == grade) {
            log.error("Unable to delete. Grade with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        gradeService.deleteGrade(grade);
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



}
