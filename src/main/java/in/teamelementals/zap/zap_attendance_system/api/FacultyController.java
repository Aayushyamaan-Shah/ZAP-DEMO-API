package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.FacultyDAO;
import in.teamelementals.zap.zap_attendance_system.model.FacultyUserCustom;
import in.teamelementals.zap.zap_attendance_system.model.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@Qualifier("postgresRepo")
@PreAuthorize("hasRole('ADMIN') || hasRole('HR')")
@RequestMapping(path = "/zap/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyDAO facultyDAO;

    @PostMapping("/add")
    public ResponseEntity<Integer> addFaculty(@RequestBody FacultyUserCustom facultyUserCustom){
        int a = facultyDAO.insert(facultyUserCustom);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @PutMapping("/update/{facultyId}")
    public ResponseEntity<Integer> updateFaculty(
            @PathVariable("facultyId") int facultyid,
            @RequestBody FacultyUserCustom facultyUserCustom
    ){
        int a = facultyDAO.update(facultyid,facultyUserCustom);
        return new ResponseEntity<>(a,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{facultyId}/{modifierId}")
    public ResponseEntity<Integer> deleteFaculty(
            @PathVariable("facultyId") int facultyid,
            @PathVariable("modifierId") int modifierId){
       facultyDAO.delete(facultyid,modifierId);
       return new ResponseEntity<>(1,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<FacultyUserCustom>> getAllFaculty(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "facultySysid",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<FacultyUserCustom> facultyUserCustomList = facultyDAO.getAllFaculty(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(facultyUserCustomList,HttpStatus.OK);
    }

    @GetMapping("/get/{facultyId}")
    public ResponseEntity<FacultyUserCustom> getFaculty(@PathVariable int facultyId){
        FacultyUserCustom facultyUserCustom = facultyDAO.getFaculty(facultyId);
        return new ResponseEntity<>(facultyUserCustom,HttpStatus.OK);
    }
}
