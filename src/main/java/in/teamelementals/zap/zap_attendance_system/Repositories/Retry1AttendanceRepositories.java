package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Retry1Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface Retry1AttendanceRepositories extends JpaRepository<Retry1Attendance,Integer> {

    List<Retry1Attendance> findByFacultyAndCreatedDateBetweenAndStudentIn(String facultyId, Timestamp date1, Timestamp date2 , List<String> students);

}
