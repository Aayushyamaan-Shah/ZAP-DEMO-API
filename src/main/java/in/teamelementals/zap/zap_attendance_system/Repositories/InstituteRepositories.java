package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepositories extends JpaRepository<Institute,Integer> {
    List<Institute> findBySysStatus(String sysStatus);
    boolean searchByNameAndSysStatus(String name,String sysStatus);
}
