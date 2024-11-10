package labs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.time.LocalDate;

@Setter
@Getter
public class Tutor {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Language language;
    private double salary;

    public Tutor(String firstName, String lastName, LocalDate birthDate, Language language, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.language = language;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", language=" + language +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Double.compare(tutor.salary, salary) == 0 &&
                firstName.equals(tutor.firstName) &&
                lastName.equals(tutor.lastName) &&
                birthDate.equals(tutor.birthDate) &&
                language == tutor.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, language, salary);
    }
}
