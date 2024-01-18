package in.teamelementals.zap.zap_attendance_system.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "device_sysid")
    Integer deviceId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "sys_status")
    String sysStatus;
    @Column(name = "creator_id")
    int creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;
    @Column(name = "modified_user_id")
    Integer modifierId;
    String name;
    String status;
    String mac;
    @Column(name = "firmware_version")
    String firmwareVersion;
    Integer number;
}
