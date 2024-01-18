package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewgeneralattendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface viewgeneralattendanceRepositories extends JpaRepository<viewgeneralattendance,Integer> {

    List<viewgeneralattendance> findByEnrollment(String userid);
    List<viewgeneralattendance> findByNameLike(String name);

    List<viewgeneralattendance> findByEnrollmentLikeAndNameLikeAndInstituteLikeAndLocationLikeAndCreatedDateBetween(String userId, String name, String institute, String location, Date d1,Date d2);
    List<viewgeneralattendance> findByEnrollmentLikeAndNameLikeAndInstituteLikeAndLocationLike(String userId,String name,String institute,String location);

    List<viewgeneralattendance> findByEnrollmentLikeAndNameLikeAndInstituteLikeAndLocationLikeAndBuildingNameLikeAndAndBuildingNumberLikeAndInTimeBetweenAndInTimeBetween(String userId, String name, String institute, String location,String buildingName,String buildingNumber, Timestamp d1, Timestamp d2, Timestamp t1,Timestamp t2);
}
