package itp.instituto.customer.service;

import itp.instituto.customer.entity.Grade;

import java.util.List;

public interface GradeService {
    public List<Grade> findGradeAll();

    public Grade createGrade(Grade grade);

    public Grade updateGrade(Grade grade);

    public void deleteGrade(Grade grade);

    public Grade getGrade (Long id);
}
