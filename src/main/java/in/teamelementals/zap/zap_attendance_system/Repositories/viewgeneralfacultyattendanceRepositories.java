package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewgeneralfacultyattendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface viewgeneralfacultyattendanceRepositories extends JpaRepository<viewgeneralfacultyattendance,Integer> {


    List<viewgeneralfacultyattendance> findByEnrollmentNoLikeAndNameLikeAndInstituteLikeAndLocationLikeAndDepartmentLikeAndDesignationLikeAndCreatedDateBetweenAndInTimeBetween(String userId, String name, String institute, String location, String department, String designation, Date d1,Date d2,Timestamp t1,Timestamp t2);

}
