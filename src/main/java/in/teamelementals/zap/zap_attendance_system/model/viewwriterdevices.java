package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "viewwriterdevices")
@IdClass(viewwriterdevicesId.class)
public class viewwriterdevices {

    @Id
    private Integer number;

    private  String name;

    @Column(name = "mac_address")
    private String macAddress;

    @Id
    @Column(name = "accessible_role")
    private String accessibleRole;

}
