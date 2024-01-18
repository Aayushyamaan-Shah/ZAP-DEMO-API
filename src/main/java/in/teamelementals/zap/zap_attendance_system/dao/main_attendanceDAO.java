package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.main_attendance;

import java.util.List;

public interface main_attendanceDAO{

    Integer insertUser(main_attendance mainAttendance);

    List<getAllUser> getAllUser();
}
