package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.StudentAgeValidator;
import bll.validators.Validator;
import dao.StudentDAO;
import model.Student;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class StudentBLL {

    private List<Validator<Student>> validators;
    private StudentDAO studentDAO;

    public StudentBLL() {
        validators = new ArrayList<Validator<Student>>();
        validators.add(new EmailValidator());
        validators.add(new StudentAgeValidator());

        studentDAO = new StudentDAO();
    }

    public Student findStudentById(int id) {
        Student st = studentDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The student with id = " + id + " was not found!");
        }
        return st;
    }
    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }
    public void createStudent(Student student) {
        studentDAO.insert(student);
    }
    public void updateStudent(Student student) {
        studentDAO.update(student);
    }


}
