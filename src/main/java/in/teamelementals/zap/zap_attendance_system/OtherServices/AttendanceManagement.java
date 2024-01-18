package in.teamelementals.zap.zap_attendance_system.OtherServices;

import in.teamelementals.zap.zap_attendance_system.Repositories.in0utFacultyAttendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.in_out_attendanceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceManagement {

    @Autowired
    private in_out_attendanceRepositories inOutAttendanceRepositories;

    @Autowired
    private in0utFacultyAttendanceRepositories in0utFacultyAttendanceRepositories;

    public void handleOutTime(){


    }


}
