package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "writer_devices")
public class writerDevices {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "writer_devices_sysid")
    int writerDevicesSysid;

    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    Integer creatorId;

    @Column(name = "accessible_role")
    String role;

    @Column(name = "sys_status")
    String sysStatus;

}
