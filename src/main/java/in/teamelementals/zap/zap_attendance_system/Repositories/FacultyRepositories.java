package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepositories extends JpaRepository<Faculty,Integer>{

    List<Faculty> findBySysStatus(String sysStatus);

}
