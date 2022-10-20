package itp.instituto.customer.repository;

import itp.instituto.customer.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    public Grade findByName(String name);
}
