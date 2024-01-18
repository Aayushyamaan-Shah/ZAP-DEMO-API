package in.teamelementals.zap.zap_attendance_system.api;
import in.teamelementals.zap.zap_attendance_system.dao.InstituteDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.Institute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/institute")
public class InstituteController{

    @Autowired
    private InstituteDAO instituteDAO;

    @PostMapping("/add")
    public ResponseEntity<Integer> addInstitute(@RequestBody Institute institute){
       int a = instituteDAO.addInstitute(institute);
        return new ResponseEntity<>(a,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<Institute> getInstitues(){
        return instituteDAO.getInstitutes("A");
    }

    @PutMapping("/update/{instituteSysid}")
    public ResponseEntity<Integer> updateInstitute(
            @PathVariable int instituteSysid,
            @RequestBody Institute institute){
        instituteDAO.updateInstitute(instituteSysid,institute);
        return new ResponseEntity<>(1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{instituteSysid}")
    public ResponseEntity<ApiResponseMessage> deleteInstitute(@PathVariable int instituteSysid){
        instituteDAO.deleteInstitute(instituteSysid);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Institute is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
}
