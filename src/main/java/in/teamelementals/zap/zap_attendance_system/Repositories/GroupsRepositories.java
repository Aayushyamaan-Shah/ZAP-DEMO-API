package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.implementations.BuildingPostgresImpl;
import in.teamelementals.zap.zap_attendance_system.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupsRepositories extends JpaRepository<Groups,Integer> {
    List<Groups> findAllBySysStatus(String sysStatus);

    Boolean existsByNameAndSysStatusOrDisplayNameAndSysStatus(String name,String sysStatus,String disName,String sysStatus1);

    Boolean existsByNameAndSysStatusAndGroupsSysidIsNotOrDisplayNameAndSysStatusAndGroupsSysidIsNot(String name,String sysStatus,int groupSysid,String disName,String sysStatus1,int groupSysid1);
}
