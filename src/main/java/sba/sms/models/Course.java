package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

//toString (exclude collections to avoid infinite loops)
//override equals and hashcode methods (don't use lombok here)
//        helper method

//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "course")
@ToString(exclude = {"students"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 50, name = "CourseID", nullable = false)
    private int courseId;

    @Column(length = 50, name = "CourseName", nullable = false)
    private String courseName;

    @Column(length = 50, name = "InstructorName", nullable = false)
    private String instructorName;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Course course = (Course) o;
            return courseId == course.courseId;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(courseId);
        }

}
