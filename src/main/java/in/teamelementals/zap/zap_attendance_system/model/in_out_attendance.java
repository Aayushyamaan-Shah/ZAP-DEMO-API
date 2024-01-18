package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "in_out_attendance")
public class in_out_attendance {
    @Id
    @Column(name = "in_out_attendnace_sysid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int inOutAttendnaceSysid;

    @Column(name = "user_id")
    String user;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "in_time")
    Timestamp inTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "out_time")
    Timestamp outTime;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
//    @Column(name = "created_date")
//    Date createdDate;

    @Column(name = "device_mac")
    String mac;
    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "timer_flag")
    boolean timerFlag;
}
