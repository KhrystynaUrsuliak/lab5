package labs.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Group {
    private int id;
    private String name;
    private Course course;
    private Tutor tutor;
    private List<Student> students;

    public Group(String name, Course course, Tutor tutor, List<Student> students) {
        this.name = name;
        this.course = course;
        this.tutor = tutor;
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", course=" + course +
                ", tutor=" + tutor +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.name) && course.equals(group.course) && tutor.equals(group.tutor) && students.equals(group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course, tutor, students);
    }
}
