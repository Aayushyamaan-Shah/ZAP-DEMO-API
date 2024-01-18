package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.DepartmentBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentBuildingRepositories extends JpaRepository<DepartmentBuilding,Integer>{

    Boolean existsBySysStatusAndDepartment(String sysStatus,int department);

    List<DepartmentBuilding> findByDepartment(int department);
}
