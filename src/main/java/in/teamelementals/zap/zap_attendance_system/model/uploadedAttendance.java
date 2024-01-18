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
@Table(name = "uploaded_attendance")
public class uploadedAttendance{

    @Id
    @Column(name = "uploaded_attendance_sysid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int mainAttendanceSysid;

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
