package in.teamelementals.zap.zap_attendance_system.Repositories;
import in.teamelementals.zap.zap_attendance_system.model.main_attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.util.List;

public interface main_attendanceRepositories extends JpaRepository<main_attendance,Integer> {

    List<main_attendance> findByFacultyAndCreatedDateBetweenAndStudentIn(String facultyId, Timestamp date1, Timestamp date2 , List<String> students);

    Integer countByCreatedDateBetween(Timestamp createdDate1,Timestamp createdDate2);

}
