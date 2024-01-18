package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.in_out_attendance;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface in_out_attendanceDAO {

    Integer insertUser(in_out_attendance inOutAttendance);

    List<getAllUser> getAllUser();

//    List<in_out_attendance> getUserByTimeStamp(Timestamp t1, Timestamp t2);

}
