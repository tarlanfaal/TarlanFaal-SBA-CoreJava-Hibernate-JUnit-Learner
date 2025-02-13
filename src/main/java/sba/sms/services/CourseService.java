package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;

import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    private SessionFactory sessionFactory;

    public CourseService() {this.sessionFactory = sessionFactory;}

    @Override
    public void createCourse(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            return (session.createQuery("from Course", Course.class).list());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}