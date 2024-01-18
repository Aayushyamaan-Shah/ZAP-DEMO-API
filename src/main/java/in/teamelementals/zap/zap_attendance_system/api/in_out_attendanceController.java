package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.in_out_attendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.in_out_attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/in_out")
public class in_out_attendanceController{

    @Autowired
    private in_out_attendanceDAO inOutAttendanceDAO;
    @PostMapping("/add")
    public Integer insertUser(
            @RequestBody in_out_attendance inOutAttendance
    ){
        return inOutAttendanceDAO.insertUser(inOutAttendance);
    }

    @GetMapping("/getStudent")
    public List<getAllUser>  getAllUser(){
        return inOutAttendanceDAO.getAllUser();
    }

//    @GetMapping("/getUserByTimeStamp")
//    public List<in_out_attendance> getUserByTimeStamp(
//            @RequestParam(value = "Time1") Timestamp t1,
//            @RequestParam(value = "Time2") Timestamp t2
//    ){
//        return inOutAttendanceDAO.getUserByTimeStamp(t1,t2);
//    }

}
