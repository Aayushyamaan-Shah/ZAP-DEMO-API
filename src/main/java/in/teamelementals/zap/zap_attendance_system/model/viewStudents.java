package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "viewstudents")
public class viewStudents {

    @Id
    @Column(name = "user_sysid")
    int userSysid;

    @Column(name = "user_created_date")
    Timestamp createdDate;

    @Column(name = "user_creator_id")
    Integer creatorId;

    @Column(name = "users_modified_date")
    Timestamp modifiedDate;

    @Column(name = "users_modified_user_id")
    Integer modifiedUserid;

    @Column(name = "users_sys_status")
    String sysStatus;

    @Column(name = "account_status")
    String accountStatus;

    String name;

    String contact;

    String email;

    String role;

    @Column(name = "password_hash")
    String passwordHash;

    String salt;

    @Column(name = "enrollment_no")
    String enrollmentNo;

    @Column(name = "student_sysid")
    Integer studentSysid;

    @Column(name = "student_created_date")
    Timestamp studentCreatedDate;

    @Column(name = "student_modified_date")
    Timestamp studentModifiedDate;

    @Column(name = "student_sys_status")
    String studentSysStatus;

    String institute;

    @Column(name = "program_type")
    String programType;

    @Column(name = "admission_semester")
    Integer admissionSemester;

    String degree;

    @Column(name = "current_semester")
    Integer currentSemester;

    String division;

    String batch;

    @Column(name = "admission_type")
    String admissionType;

    @Column(name = "date_of_joining")
    Date dateOfJoining;

}
