package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.uploadedAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface uploadedAttendanceRepositories extends JpaRepository<uploadedAttendance,Integer> {
}
