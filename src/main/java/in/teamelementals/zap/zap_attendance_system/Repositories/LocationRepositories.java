package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepositories extends JpaRepository<Location,Integer>{

    List<Location> findByFloorAndBuildingAndSysStatus(int floor,int building,String sysStatus);
    List<Location> findByBuildingAndSysStatus(int buildingId,String sysStatus);
    Boolean existsByNumberAndSysStatus(String number,String sysStatus);
    Boolean existsByNumberAndSysStatusAndLocationSysidIsNot(String number,String sysStatus,int LocationSysid1);
}
