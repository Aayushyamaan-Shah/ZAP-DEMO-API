package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.DepartBuildingCustom;
import in.teamelementals.zap.zap_attendance_system.model.DepartmentBuilding;

import java.util.List;

public interface DepartmentBuildingDAO {

    Boolean add(DepartmentBuilding departmentBuilding);

    List<DepartBuildingCustom> getByDepartment(int department);
}
