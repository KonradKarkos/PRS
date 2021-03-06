package dtos;

import entities.DeanGroup;
import entities.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private DeanGroup deanGroup;
    private String eMail;
    private String faculty;
    private String deanGroupName;
    private String cardId;
    private Boolean isLate;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.deanGroup = student.getDeanGroup();
        this.eMail = student.getEMail();
        this.deanGroupName = student.getDeanGroup().getDeanName();
        this.faculty = student.getDeanGroup().getFaculty().getFacultyName();
    }

    public Student toEntity() {
        return new Student(id, firstName, lastName, deanGroup, eMail);
    }
}
