package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

import java.util.HashSet;

import static org.junit.Assert.*;


class StudentServiceTest {

    private static StudentService studentService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        studentService = new StudentService();
        CommandLine.addData();
    }

    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setStudentEmail("meredith@gmail.com");
        student.setStudentName("Meredith Grey");
        student.setStudentPassword("password");

        studentService.createStudent(student);
        Student fetchedStudent = studentService.getStudentByEmail(student.getStudentEmail());
        assertEquals("Email should match the one assigned", fetchedStudent.getStudentEmail(), "nancy@gmail.com");

    }
}

//
//    @Test
//    public void testCreateStudent() {
//
//        Student student = new Student();
//
//        StudentService.createStudent(student);
//        Student fetchedStudent = StudentService.getStudentByEmail(student.getStudentEmail());
//        assertNotNull(String.valueOf(fetchedStudent), "Fetched student should not be null");
//        assertEquals("meredith@gmail.com", fetchedStudent.getStudentEmail(), "Email must match expected value");
//
////        Student student = new Student();
////        student.setStudentEmail("meredith@gmail.com");
////        student.setStudentName("Meredith Grey");
////        student.setStudentPassword("password");
////        studentService.createStudent(student);
////
////        Student fetchedStudent = studentService.getStudentByEmail(student.getStudentEmail()Email());
////        assertEquals("Email must match file email",fetchedStudent.getStudentEmail(),"meredith@gmail.com");
//
