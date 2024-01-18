package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepositories extends JpaRepository<Department,Integer>{

    List<Department> findByInstitute(Integer institute);
}
