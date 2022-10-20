package itp.instituto.customer.service;

import itp.instituto.customer.entity.Grade;
import itp.instituto.customer.repository.GradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class GradeServicelmpl implements GradeService{

    @Autowired
    GradeRepository gradeRepository;

    @Override
    public List<Grade> findGradeAll() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade createGrade(Grade grade) {
        Grade gradeDB = gradeRepository.findByName(grade.getName());
        if (gradeDB != null){
            return gradeDB;
        }
        grade.setState("CREATED");
        gradeDB = gradeRepository.save(grade);
        return gradeDB;
    }

    @Override
    public Grade updateGrade(Grade grade) {
        Grade gradeDB = getGrade(grade.getId());
        if (gradeDB == null){
            return null;
        }
        gradeDB.setName(grade.getName());
        gradeDB.setState("UPDATED");
        //grade.setState();
        return gradeRepository.save(gradeDB);
    }

    @Override
    public void deleteGrade(Grade grade) {
        Grade gradeDB = getGrade(grade.getId());
        if (gradeDB != null){
            gradeRepository.delete(grade);
        }


        //return gradeRepository.deleteById(grade.getId());

    }

    @Override
    public Grade getGrade(Long id) {
        return gradeRepository.findById(id).orElse(null);
    }
}
