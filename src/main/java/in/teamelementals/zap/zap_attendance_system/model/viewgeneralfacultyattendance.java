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

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "viewgeneralfacultyattendance")
public class viewgeneralfacultyattendance {

    @Id
    @Column(name = "in_out_faculty_attendance_sysid")
    int inOutFacultyAttendanceSysid;

    String mac;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name ="in_time")
    Timestamp inTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "out_time")
    Timestamp outTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Date createdDate;

    @Column(name = "enrollment_no")
    String enrollmentNo;

    String name;

    String location;

    String institute;

    String department;

    String designation;

    @Column(name = "building_name")
    String buildingName;

    @Column(name = "building_number")
    String buildingNumber;
}
