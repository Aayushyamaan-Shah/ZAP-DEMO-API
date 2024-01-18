package in.teamelementals.zap.zap_attendance_system.model;

import io.swagger.models.auth.In;
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
@Table(name = "viewfaculties")
public class viewFaculties {

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

    @Column(name = "faculty_sysid")
    Integer facultySysid;

    @Column(name = "faculty_created_date")
    Timestamp facultyCreatedDate;

    @Column(name = "faculty_modified_date")
    Timestamp facultyModifiedDate;

    @Column(name = "faculty_modified_user_id")
    Integer facultyModifiedUserid;

    @Column(name = "faculty_sys_status")
    String facultySysStatus;

    @Column(name = "faculty_egov_id")
    String facultyEgovid;

    @Column(name = "program_type")
    String programType;

    Integer institute;

    @Column(name = "date_of_joining")
    Date dateOfJoining;

    String department;

    String designation;

    @Column(name = "reporting_authority")
    Integer reportingAuthority;

    @Column(name = "institute_name")
    String instituteName;

}
