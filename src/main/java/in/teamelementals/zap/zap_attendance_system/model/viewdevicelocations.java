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
@Table(name = "viewdevices")
public class viewdevicelocations {

    @Column(name = "device_location_sysid")
    int deviceLocationSysid;

    @Id
    Integer device;

    Integer location;

    @Column(name = "location_name")
    String locationName;

    String mac;

    String status;

    int number;

}
