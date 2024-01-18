package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @Column(name = "faculty_sysid")
    Integer facultySysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    Integer modifiedUserid;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "program_type")
    String programType;

    Integer institute;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "IST")
    @Column(name = "date_of_joining")
    Date dateOfJoining;

    String department;

    String designation;

    @Column(name = "reporting_authority")
    Integer reportingAuthority;

}
