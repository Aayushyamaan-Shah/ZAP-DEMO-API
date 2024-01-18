package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.writerDevices;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface writerDevicesRepositories extends JpaRepository<writerDevices,Integer> {

    Boolean existsBySysStatus(String sysStatus);

    writerDevices findBySysStatus(String sysStatus);

}
