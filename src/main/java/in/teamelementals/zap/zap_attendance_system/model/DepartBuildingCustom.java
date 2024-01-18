package in.teamelementals.zap.zap_attendance_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartBuildingCustom {

    Integer departmentBuildingSysid;

    Integer department;

    Integer building;

    String name;

    String number;
}
