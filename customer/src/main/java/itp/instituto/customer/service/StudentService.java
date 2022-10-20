package itp.instituto.customer.service;

import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> findStudentAll();

    public List<Student> findStudentsByGrade(Grade grade);

    public Student createStudent(Student student);
    public Student updateStudent(Student student);
    public void deleteStudent(Student student);
    public  Student getStudent(Long id);



}
