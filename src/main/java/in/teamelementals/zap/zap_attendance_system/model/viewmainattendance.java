package in.teamelementals.zap.zap_attendance_system.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "viewmainattendance")
public class viewmainattendance {

    @Id
    @Column(name = "main_attendance_sysid")
    Integer mainAttendanceSysid;

    @Column(name = "location")
    String location;

    @Column(name = "device_id")
    String deviceId;

    @Column(name = "faculty_id")
    String facultyId;

    @Column(name = "faculty_name")
    String facultyName;

    @Column(name = "faculty_designation")
    String facultyDesignation;

    @Column(name = "student_id")
    String studentId;

    @Column(name = "student_name")
    String studentName;

    @Column(name = "student_institute")
    String studentInstitute;

    @Column(name = "student_program_type")
    String studentProgramType;

    @Column(name = "student_degree")
    String studentDegree;

    @Column(name = "student_current_semester")
    String studentCurrentSemester;

    @Column(name = "student_division")
    String studentDivision;

    @Column(name = "student_batch")
    String studentBatch;

    @Column(name = "created_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    Timestamp createdDate;

    @Column(name = "egov_status")
    String egovStatus;

}
