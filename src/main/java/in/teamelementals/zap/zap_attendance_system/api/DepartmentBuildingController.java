package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.DepartmentBuildingDAO;
import in.teamelementals.zap.zap_attendance_system.model.DepartBuildingCustom;
import in.teamelementals.zap.zap_attendance_system.model.DepartmentBuilding;
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
@RequestMapping(path = "/zap/api/departBuilding")
public class DepartmentBuildingController {

    @Autowired
    private DepartmentBuildingDAO departmentBuildingDAO;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody DepartmentBuilding departmentBuilding){
       Boolean a = departmentBuildingDAO.add(departmentBuilding);
       return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{departId}")
    public ResponseEntity<List<DepartBuildingCustom>> getById(@PathVariable int departId){
        List<DepartBuildingCustom> departBuildingCustomList = departmentBuildingDAO.getByDepartment(departId);
        return new ResponseEntity<>(departBuildingCustomList,HttpStatus.OK);
    }
}
