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
@Table(name = "grouped_devices")
public class GroupedDevices {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "grouped_devices_sysid")
    Integer groupedDevicesSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;
    @Column(name = "modified_user_id")
    Integer modifiedUserId;
    @Column(name = "sys_status")
    String sysStatus;
    @Column(name = "group_id")
    Integer groupId;
    Integer device;
}
