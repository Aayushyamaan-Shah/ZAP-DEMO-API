package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.in0utFacultyAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface in0utFacultyAttendanceRepositories extends JpaRepository<in0utFacultyAttendance,Integer> {

    boolean existsByUserAndOutTimeAndMacAndTimerFlag(String userId, Time time,String mac,boolean timerFlag);
    in0utFacultyAttendance findByUserAndOutTimeAndMacAndTimerFlag(String userId, Time time,String mac,boolean timerFlag);
    List<in0utFacultyAttendance> findByCreatedDateBetween(Timestamp t1, Timestamp t2);

    Integer countByCreatedDateBetween(Timestamp createdDate1,Timestamp createdDate2);

}
