package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
@Table(name = "viewgeneralattendance")
public class viewgeneralattendance{

    @Id
    int in_out_attendnace_sysid;
    @Column(name = "enrollment_no")
    String enrollment;
    String name;
    String institute;
    String program_type;
    String degree;
    String division;
    String batch;
    @Column(name = "current_semester")
    int currentSemester;
    @Column(name = "mac",insertable = false,updatable = false)
    String device_id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "in_time")
    Timestamp inTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "out_time")
    Timestamp outTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Date createdDate;

    String mac;
    String location;

    @Column(name = "building_name")
    String buildingName;

    @Column(name = "building_number")
    String buildingNumber;

    @Column(name = "timer_flag")
    boolean timerFlag;

}
