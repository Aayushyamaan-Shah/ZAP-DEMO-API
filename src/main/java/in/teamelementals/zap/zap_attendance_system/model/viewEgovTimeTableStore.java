package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "viewegovtimetablestore")
public class viewEgovTimeTableStore{

    @Id
    @Column(name = "timetable_id")
    String timeTableId;

    @Column(name = "timeslot")
    String timeSlot;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "IST")
    Date date;

    String location;

    @Column(name = "subject_name")
    String subjectName;

    @Column(name = "faculty_id")
    String facultyId;

    @Column(name = "faculty_name")
    String facultyName;

    String institute;

    @Column(name = "lecture_details")
    String lectureDetails;

    @Column(name = "all_students")
    String allStudents;

}
