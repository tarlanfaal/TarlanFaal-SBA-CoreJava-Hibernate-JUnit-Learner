package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "student")
@ToString(exclude = {"courses"})
public class Student {

    @Id
    @Column(length = 50, name = "StudentEmail", nullable = false)
    private String studentEmail;

    @Column(length = 50, name = "StudentName", nullable = false)
    private String studentName;

    @Column(length = 50, name = "StudentPassword", nullable = false)
    private String studentPassword;

    @JoinTable(name = "Student_Courses",
            joinColumns = @JoinColumn(name = "studentEmail"),
            inverseJoinColumns = @JoinColumn(name = "CoursesID" ))
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Course> courses = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentEmail.equals(student.studentEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentEmail);
    }


}



