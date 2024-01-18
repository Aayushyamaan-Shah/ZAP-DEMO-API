package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.EgovTimetableStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EgovTimetableStoreRepositories extends JpaRepository<EgovTimetableStore,Integer> {

    List<EgovTimetableStore> findByTtdateAndTttimeLikeAndFacultyIdLikeAndFacultyNameLike(Date date,String tttime,String facultyId,String facultyName);

    @Query(value = "SELECT DISTINCT ttdate FROM egov_timetable WHERE faculty_id = :facultyId", nativeQuery = true)
    List<String> getDate(@Param("facultyId") String facultyId);
}
