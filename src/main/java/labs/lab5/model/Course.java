package labs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
public class Course {
    private int id;
    private String name;
    private Language language;
    private String level;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;

    public Course(String name, Language language, String level, LocalDate startDate, LocalDate endDate, double price) {
        this.name = name;
        this.language = language;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", language=" + language +
                ", level='" + level + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Double.compare(course.price, price) == 0 &&
                Objects.equals(name, course.name) &&
                language == course.language &&
                Objects.equals(level, course.level) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, language, level, startDate, endDate, price);
    }
}
