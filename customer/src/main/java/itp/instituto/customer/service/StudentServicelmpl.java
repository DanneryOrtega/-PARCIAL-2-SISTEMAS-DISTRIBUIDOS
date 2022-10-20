package itp.instituto.customer.service;

import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.entity.Student;
import itp.instituto.customer.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class StudentServicelmpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> findStudentAll(){
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findStudentsByGrade(Grade grade) {
        return studentRepository.findByGrade(grade);
    }

    @Override
    public Student createStudent(Student student) {
        Student studentDB = studentRepository.findByNumberID(student.getNumberID());
        if (studentDB != null){
            return studentDB;
        }

        student.setState("CREATED");
        studentDB = studentRepository.save(student);
        return studentDB;
    }

    @Override
    public Student updateStudent(Student student) {
        Student studentDB = getStudent(student.getId());
        if (studentDB == null){
            return null;
        }
        studentDB.setFirstName(student.getFirstName());
        studentDB.setLastName(student.getLastName());
        studentDB.setEmail(student.getEmail());
        studentDB.setPhotoUrl(student.getPhotoUrl());
        return studentRepository.save(studentDB);
    }

    @Override
    /*public Student deleteStudent(Student student) {
        Student studentDB = getStudent(student.getId());
        if (studentDB == null){
            return null;
        }
        student.setState("DELETED");
        return studentRepository.save(student);
    }

     */
    public void deleteStudent(Student student) {
        Student studentDB = getStudent(student.getId());
        if (studentDB != null) {
            studentRepository.delete(student);
        }
    }
    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

}
