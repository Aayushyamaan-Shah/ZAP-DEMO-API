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
@Table(name = "retry_2_attendance")
public class Retry2Attendance {

    @Id
    @Column(name = "retry_2_attendance_sysid")
    int retry2AttendanceSysid;

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

}
