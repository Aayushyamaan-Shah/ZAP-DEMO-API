package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.in0utFacultyAttendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.inOutFacultyCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/inOutfaculty")
public class in0utFacultyAttendanceController{

    @Autowired
    private in0utFacultyAttendanceDAO in0utFacultyAttendanceDAO;
    @PostMapping("/add")
    public Integer insertUser(
            @RequestBody inOutFacultyCustom inOutFacultyCustom
    ){
        return in0utFacultyAttendanceDAO.insert(inOutFacultyCustom);
    }




}

