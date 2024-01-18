package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewEgovTimeTableStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface viewEgovTimeTableStoreRepositories extends JpaRepository<viewEgovTimeTableStore,String> {

    @Query(value = "SELECT DISTINCT date FROM egovtimetable WHERE faculty_id like :facultyId", nativeQuery = true)
    List<String> getDate(@Param("facultyId") String facultyId);

    @Query(value = "SELECT timeslot|| ' / ' ||subject_name|| ' (' ||lecture_details|| ')' FROM egovtimetable WHERE faculty_id like :facultyId and date = :date", nativeQuery = true)
    List<String> getTimedLectureDetails(@Param("facultyId") String facultyId,@Param("date") Date date);

    List<viewEgovTimeTableStore> findByDateAndTimeSlotAndLectureDetails(Date date, String timeSlot,String lectureDetails);

}
