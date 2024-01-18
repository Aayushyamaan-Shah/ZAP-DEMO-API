package in.teamelementals.zap.zap_attendance_system.api;
import in.teamelementals.zap.zap_attendance_system.Repositories.viewmainattendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewmainattendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;


@CrossOrigin
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewmain")
public class viewmainattendanceController{

    //viewmain
    @Autowired
    private viewmainattendanceRepositories viewmainattendanceRepositories;

    @GetMapping("/getStudent")
    public List<viewmainattendance> getAllUser(
            @RequestParam(name = "studentId",defaultValue = "%",required = false) String studentId,
            @RequestParam(name = "facultyId",defaultValue = "-1",required = false) String facultyId,
            @RequestParam(name = "studentName",defaultValue = "%",required = false) String studentName,
            @RequestParam(name = "facultyName",defaultValue = "%",required = false) String facultyName,
            @RequestParam(name = "location",defaultValue = "%",required = false) String location,
            @RequestParam(name = "institute",defaultValue = "%",required = false) String institute,
            @RequestParam(name = "dateFrom",defaultValue = "2000-05-05 22:46:58.777",required = false) Timestamp dateFrom,
            @RequestParam(name = "dateTo",defaultValue = "2000-05-05 22:46:58.777",required = false) Timestamp dateTo
            ){

            if(facultyId.equals("-1")){
                List<viewmainattendance> list = viewmainattendanceRepositories.findByStudentIdLikeAndStudentNameLikeAndFacultyNameLikeAndLocationLikeAndStudentInstituteLikeAndCreatedDateBetween(
                        "%"+studentId.toUpperCase()+"%","%"+studentName.toUpperCase()+"%","%"+facultyName.toUpperCase()+"%","%"+location.toUpperCase()+"%","%"+institute.toUpperCase()+"%",dateFrom,dateTo
                );
                return list;
            }
            else {
                List<viewmainattendance> list = viewmainattendanceRepositories.findByStudentIdLikeAndStudentNameLikeAndFacultyIdAndFacultyNameLikeAndLocationLikeAndStudentInstituteLikeAndCreatedDateBetween(
                        "%" + studentId.toUpperCase() + "%", "%" + studentName.toUpperCase() + "%",facultyId.toUpperCase(), "%" + facultyName.toUpperCase() + "%", "%" + location.toUpperCase() + "%", "%" + institute.toUpperCase() + "%", dateFrom, dateTo
                );
                return list;
            }
    }
}
