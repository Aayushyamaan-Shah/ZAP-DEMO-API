package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.OtherServices.AttendanceUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
@Qualifier("postgresRepo")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/zap/api/egov")
public class EGovController {

    @Autowired
    private AttendanceUpload attendanceUpload;

    @PostMapping("/call")
    public ResponseEntity<Integer> apiCall(){
        attendanceUpload.ZapToEgovAttendance();
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

}
