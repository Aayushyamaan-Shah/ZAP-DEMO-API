package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.main_attendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.main_attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/main_attendance")
public class main_attendanceController{
    @Autowired
    private main_attendanceDAO mainAttendanceDAO;
    @PostMapping("/add")
    public Integer insertUser(
            @RequestBody main_attendance mainAttendance
    ){
        return mainAttendanceDAO.insertUser(mainAttendance);
    }

    @GetMapping("/getStudent")
    public List<getAllUser> getAllUser(){
        return mainAttendanceDAO.getAllUser();
    }
}
