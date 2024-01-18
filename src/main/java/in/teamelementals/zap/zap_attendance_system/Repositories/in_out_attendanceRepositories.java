package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.in_out_attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface in_out_attendanceRepositories extends JpaRepository<in_out_attendance,Integer> {
    boolean existsByUserAndOutTimeAndMacAndTimerFlag(String userId,Time time,String mac,boolean timerFlag);
    in_out_attendance findByUserAndOutTimeAndMacAndTimerFlag(String userId,Time time,String mac,boolean timerFlag);
//    List<in_out_attendance> findByCreatedDateBetween(Timestamp t1,Timestamp t2);
    Integer countByInTimeBetween(Timestamp ts1,Timestamp ts2);

//    List<in_out_attendance> findByOu
}
