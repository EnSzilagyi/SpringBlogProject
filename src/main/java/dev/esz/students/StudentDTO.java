package dev.esz.students;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name = "student")
public class StudentDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String university;
    private Integer year;

    public StudentDTO() {}



    public StudentDTO( String firstname, String lastname, String university, Integer year) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.university = university;
        this.year = year;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
