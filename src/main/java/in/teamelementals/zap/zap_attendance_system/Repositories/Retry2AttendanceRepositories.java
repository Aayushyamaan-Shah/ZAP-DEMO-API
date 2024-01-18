package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Retry2Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface Retry2AttendanceRepositories extends JpaRepository<Retry2Attendance,Integer> {

    List<Retry2Attendance> findByFacultyAndCreatedDateBetweenAndStudentIn(String facultyId, Timestamp date1, Timestamp date2 , List<String> students);
}
