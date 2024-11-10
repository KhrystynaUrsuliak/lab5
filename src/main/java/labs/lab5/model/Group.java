package labs.lab5.model;

import java.util.Objects;
import java.util.List;

public class Group {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
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
