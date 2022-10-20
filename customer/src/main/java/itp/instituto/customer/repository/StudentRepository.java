package itp.instituto.customer.repository;

import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findByNumberID(String numberID);
    public List<Student> findByLastName(String lastName);
    public List<Student> findByGrade(Grade grade);
}
