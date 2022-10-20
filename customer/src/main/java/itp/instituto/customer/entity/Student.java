package itp.instituto.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="tbl_student")

public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_id" , unique = true ,length = 8, nullable = false)
    private String numberID;


    @Column(name="first_name", nullable=false)
    private String firstName;


    @Column(name="last_name", nullable=false)
    private String lastName;


    @Column(unique=true, nullable=false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Grade grade;

    private String state;
}
