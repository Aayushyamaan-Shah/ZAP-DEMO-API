package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role{
    @Id
    private String roleId;
    private String roleName;
}

