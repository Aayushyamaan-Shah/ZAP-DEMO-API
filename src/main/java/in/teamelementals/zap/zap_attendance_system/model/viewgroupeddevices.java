package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "viewgroupeddevices")
public class viewgroupeddevices{

    @Id
    @Column(name = "grouped_devices_sysid")
    Integer groupedDevicesSysid;

    @Column(name = "group_id")
    Integer groupId;

    @Column(name = "device")
    Integer device;

    @Column(name = "number")
    Integer number;

    String mac;
}
