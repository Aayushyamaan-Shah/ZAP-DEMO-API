package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.GroupedDevices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupedDevicesRepositories extends JpaRepository<GroupedDevices,Integer> {
    List<GroupedDevices> findByGroupIdAndSysStatus(int groupId,String sysStatus);

    List<GroupedDevices> findByGroupId(int groupId);

    Boolean existsByDeviceAndSysStatus(int deviceId,String sysStatus);

    Boolean existsByDeviceAndSysStatusAndGroupedDevicesSysid(int deviceId,String sysStatus,int groudDeviceSysid);
}
