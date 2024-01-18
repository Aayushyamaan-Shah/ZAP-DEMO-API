package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.DeviceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceLocationRepositories extends JpaRepository<DeviceLocation,Integer> {

    List<DeviceLocation> findByLocationAndSysStatus(int locationId,String sysStatus);
    Boolean existsByDeviceAndSysStatus(int deviceId,String sysStatus);

    Boolean existsByDeviceAndSysStatusAndDeviceLocationSysidIsNot(int deviceId,String sysStatus,int deviceSysid);
}
