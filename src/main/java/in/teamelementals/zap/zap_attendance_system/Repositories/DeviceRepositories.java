package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepositories extends JpaRepository<Device,Integer> {

    Boolean existsByNumberAndSysStatusOrMacAndSysStatus(int number,String sysStatus,String mac,String sysStatus1);
    Device findByMacAndSysStatus(String mac,String sysStatus);
}
