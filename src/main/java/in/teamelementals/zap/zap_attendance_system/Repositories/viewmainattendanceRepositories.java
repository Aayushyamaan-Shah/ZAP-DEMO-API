package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewmainattendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface viewmainattendanceRepositories extends JpaRepository<viewmainattendance,Integer>{

    List<viewmainattendance> findByStudentIdLikeAndStudentNameLikeAndFacultyNameLikeAndLocationLikeAndStudentInstituteLikeAndCreatedDateBetween(String studentId, String studentName,String facultyName, String location, String institute, Timestamp d1,Timestamp d2);

    List<viewmainattendance> findByStudentIdLikeAndStudentNameLikeAndFacultyIdAndFacultyNameLikeAndLocationLikeAndStudentInstituteLikeAndCreatedDateBetween(String studentId, String studentName,String facultyId,String facultyName, String location, String institute,Timestamp d1,Timestamp d2);

    List<viewmainattendance> findByFacultyIdAndCreatedDateBetweenAndStudentIdIn(String facultyId, Timestamp date1, Timestamp date2 , List<String> students);

    Integer countByCreatedDateBetween(Timestamp createdDate1,Timestamp createdDate2);

    List<viewmainattendance> findDistinctByStudentIdAndCreatedDateBetween(String studentId,Date date1,Date date2);
}
