package labs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String initialLevel;

    public Student(String firstName, String lastName, String email, String initialLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.initialLevel = initialLevel;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", initialLevel='" + initialLevel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return firstName.equals(student.firstName) && lastName.equals(student.lastName) && email.equals(student.email) && initialLevel.equals(student.initialLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, initialLevel);
    }
}
