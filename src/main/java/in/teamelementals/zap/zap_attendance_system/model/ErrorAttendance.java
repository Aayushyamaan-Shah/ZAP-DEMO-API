package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "error_attendance")
public class ErrorAttendance {

    @Id
    @Column(name = "error_attendance_sysid")
    int errorAttendanceSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "device_mac")
    String mac;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "faculty")
    String faculty;

    @Column(name = "student")
    String student;

    @Column(name = "is_touched")
    Boolean isTouched;

}