package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewgeneralfacultyattendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewgeneralfacultyattendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@PreAuthorize("hasRole('ADMIN') || hasRole('FACULTY') || hasRole('HR') || hasRole('LIBRARIAN')")
@CrossOrigin
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewfaculty")
public class viewgeneralfacultyattendanceController {

    @Autowired
    private viewgeneralfacultyattendanceRepositories viewgeneralfacultyattendanceRepositories;

    @GetMapping("/checkToken")
    public ResponseEntity<Integer> checkToken(){
        return new ResponseEntity<>(1,HttpStatus.OK);
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<viewgeneralfacultyattendance>> getAll(
            @RequestParam(name = "userId",defaultValue = "%",required = false) String userId,
            @RequestParam(name = "name",defaultValue = "%",required = false) String name,
            @RequestParam(name = "institute",defaultValue = "%",required = false) String institute,
            @RequestParam(name = "location",defaultValue = "%",required = false) String location,
            @RequestParam(name = "dateFrom", defaultValue = "#{T(java.time.LocalDate).now()}",required = false) Date dateFrom,
            @RequestParam(name = "dateTo",defaultValue = "2000-01-01",required = false) Date dateTo,
            @RequestParam(name = "timeIn",defaultValue = "2000-05-05 22:46:58.777",required = false) Timestamp timeIn,
            @RequestParam(name = "timeOut",required = false) Timestamp timeOut,
            @RequestParam(name = "department",defaultValue = "%",required = false) String department,
            @RequestParam(name = "designation",defaultValue = "%",required = false) String designation
    ){
        if(dateTo.toString().equals("2000-01-01")){
            dateTo = dateFrom;
        }

        if(timeOut==null){
            java.util.Date date = new java.util.Date();
            Timestamp ts = new Timestamp(date.getTime());
            timeOut = ts;
        }

        List<viewgeneralfacultyattendance> viewgeneralfacultyattendanceList = viewgeneralfacultyattendanceRepositories.findByEnrollmentNoLikeAndNameLikeAndInstituteLikeAndLocationLikeAndDepartmentLikeAndDesignationLikeAndCreatedDateBetweenAndInTimeBetween(userId,name,institute,location,department,designation,dateFrom,dateTo,timeIn,timeOut);
        return new ResponseEntity<>(viewgeneralfacultyattendanceList, HttpStatus.OK);
    }

}
