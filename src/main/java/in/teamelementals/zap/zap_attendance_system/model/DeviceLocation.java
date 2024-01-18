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
@Table(name = "device_location")
public class DeviceLocation{

    @Id
    @Column(name = "device_location_sysid")
    @GeneratedValue(strategy=GenerationType.AUTO)
    int deviceLocationSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    int creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modifiedDate")
    Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    Integer modifiedUserId;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "device")
    int device;

    @Column(name = "location")
    int location;
}
