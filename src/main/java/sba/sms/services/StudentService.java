package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    private SessionFactory sessionFactory;

    public StudentService() {this.sessionFactory = sessionFactory;}

    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return (session.createQuery("from Student", Student.class).list());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, email);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try (Session session = sessionFactory.openSession()) {

            if (email == null || password == null)
                return false;
            else {
                Student student = session.get(Student.class, email);
                if (student != null) {
                    if (student.getStudentPassword().equals(password))
                        return true;
                    else
                        return false;
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);

            if ((student == null) || (course == null)) {
                System.out.println("Invalid entry, student or course not found");
            }
            if (student != null && course != null) {
                if (!student.getCourses().contains(course)) {
                    student.getCourses().add(course);
                    session.merge(student);
                } else {
                    System.out.println("Student is already registered");
                }
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = sessionFactory.openSession()) {
            Student student = session.get(Student.class, email);
            Set<Course> coursesSet = student.getCourses();
            return new ArrayList<>(coursesSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
