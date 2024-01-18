package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.DepartmentDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.Department;
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
@RequestMapping(path = "/zap/api/department")
public class DepartmentController{

    @Autowired
    private DepartmentDAO departmentDAO;

    @PostMapping("/add")
    public ResponseEntity<Integer> addDepartment(@RequestBody Department department){
        departmentDAO.addDepartment(department);
        return new ResponseEntity<>(1,HttpStatus.OK);
    }

    @GetMapping("getDepartment/{instituteSysid}")
    public List<Department> getDepartmentByInstitute(@PathVariable int instituteSysid){
        return departmentDAO.getDepartmentByInstitute(instituteSysid);
    }

    @PutMapping("update/{departmentSysid}")
    public ResponseEntity<Integer> updateDepartment(
            @PathVariable int departmentSysid,
            @RequestBody Department department
    ){
        departmentDAO.updateDepartment(departmentSysid,department);
        return new ResponseEntity<>(1,HttpStatus.OK);
    }



    public ResponseEntity<ApiResponseMessage> deleteDepartment(@PathVariable int departmentSysid){
        departmentDAO.deleteDepartment(departmentSysid);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Department is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
}
