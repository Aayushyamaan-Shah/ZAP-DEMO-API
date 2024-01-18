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
@Table(name = "department_building")
public class DepartmentBuilding{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "department_building_sysid")
    Integer departmentBuildingSysid;

    @Column(name = "creator_id")
    Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss" +
            "",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    Integer modifiedUserId;

    @Column(name = "sys_status")
    String sysStatus;

    Integer department;

    Integer building;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;
}
