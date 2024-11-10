package labs.lab5;

import labs.lab5.dao.*;
import labs.lab5.exception.UniqnessException;
import labs.lab5.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            TutorDao tutorDao = new TutorDao();
            CourseDao courseDao = new CourseDao();
            GroupDao groupDao = new GroupDao();
            StudentDao studentDao = new StudentDao();

            // Adding a new Tutor
            Tutor tutor = new Tutor("John", "Doe", LocalDate.of(1985, 5, 20), Language.ENGLISH, 5000.00);
            try {
                tutorDao.add(tutor);
                System.out.println("Added Tutor: " + tutor);
            } catch (UniqnessException e) {
                System.out.println("Tutor already exists: " + e.getMessage());
            }

            // Adding a new Course
            Course course = new Course("English Basic", Language.ENGLISH, "A1", LocalDate.of(2023, 9, 1), LocalDate.of(2024, 5, 1), 1200.00);
            try {
                courseDao.add(course);
                System.out.println("Added Course: " + course);
            } catch (UniqnessException e) {
                System.out.println("Course already exists: " + e.getMessage());
            }

            // Adding new Students
            Student student1 = new Student("Alice", "Smith", "alice.smith@example.com", "A1");
            Student student2 = new Student("Bob", "Johnson", "bob.johnson@example.com", "A1");

            try {
                studentDao.add(student1);
                studentDao.add(student2);
                System.out.println("Added Students: " + student1 + ", " + student2);
            } catch (UniqnessException e) {
                System.out.println("Student already exists: " + e.getMessage());
            }

            // Adding a new Group
            List<Student> students = new ArrayList<>();
            students.add(student1);
            students.add(student2);
            Group group = new Group("Group 1", course, tutor, students);

            try {
                groupDao.add(group);
                System.out.println("Added Group: " + group);
            } catch (UniqnessException e) {
                System.out.println("Group already exists: " + e.getMessage());
            }

            // Display all Tutors
            List<Tutor> tutors = tutorDao.getAll();
            System.out.println("All Tutors:");
            tutors.forEach(System.out::println);

            // Display all Courses
            List<Course> courses = courseDao.getAll();
            System.out.println("All Courses:");
            courses.forEach(System.out::println);

            // Display all Students
            List<Student> studentsList = studentDao.getAll();
            System.out.println("All Students:");
            studentsList.forEach(System.out::println);

            // Display all Groups
            List<Group> groups = groupDao.getAll();
            System.out.println("All Groups:");
            groups.forEach(System.out::println);

            // Retrieve Student by ID
            System.out.println("\n=== Read Student by ID ===");
            int studentId = student1.getId();
            try {
                Student retrievedStudent = studentDao.getById(studentId);
                System.out.println("Retrieved Student: " + retrievedStudent);
            } catch (Exception e) {
                System.out.println("Error retrieving student by ID: " + e.getMessage());
            }

            // Update Operation
            System.out.println("\n=== Update ===");
            student1.setInitialLevel("A2");
            student1.setEmail("alice.smith@updatedemail.com");
            boolean isUpdated = studentDao.update(student1);
            if (isUpdated) {
                System.out.println("Updated Student: " + studentDao.getById(student1.getId()));
            } else {
                System.out.println("Failed to update Student.");
            }

            // Deleting a Student by ID
            System.out.println("\n=== Delete ===");
            boolean isDeleted = studentDao.deleteById(student2.getId()); // Delete student2
            if (isDeleted) {
                System.out.println("Deleted Student with ID: " + student2.getId());
            } else {
                System.out.println("Failed to delete Student.");
            }

            // Verify Deletion
            System.out.println("\n=== Verify Deletion ===");
            try {
                Student deletedStudent = studentDao.getById(student2.getId());
                System.out.println("Deleted Student still exists: " + deletedStudent);
            } catch (Exception e) {
                System.out.println("Student with ID " + student2.getId() + " does not exist (successfully deleted).");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
