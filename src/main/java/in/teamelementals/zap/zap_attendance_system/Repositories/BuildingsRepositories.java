package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Buildings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuildingsRepositories extends JpaRepository<Buildings,Integer>{
    List<Buildings> findBySysStatus(String sysStatus);

    Boolean existsBySysStatusAndNameOrNumberAndSysStatus(String sysStatus1,String name,String number,String sysStatus);

    Boolean existsByBuildingSysidIsNotAndSysStatusAndNameOrNumberAndSysStatusAndBuildingSysidIsNot(int BuildingSysid, String sysStatus1,String name,String number,String sysStatus,int BuildingSysid1);

    Buildings findByBuildingSysidAndSysStatus(int buildingId,String sysStatus);
}
