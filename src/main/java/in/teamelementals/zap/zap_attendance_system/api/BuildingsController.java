package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.BuildingsDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.Buildings;
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
@RequestMapping(path = "/zap/api/building")
public class BuildingsController{

    @Autowired
    private BuildingsDAO buildingsDAO;

    @PostMapping("/add")
    public Boolean addBuilding(@RequestBody Buildings buildings){
        return buildingsDAO.addBuilding(buildings);
    }

    @GetMapping("/getAll")
    public List<Buildings> getAll(){
        return buildingsDAO.getAll("A");
    }

    @PutMapping("/update/{buildingSysId}")
    public ResponseEntity<Boolean> updateBuilding(
            @PathVariable int buildingSysId,
            @RequestBody Buildings buildings
    ){
        Boolean a =buildingsDAO.updateBuilding(buildingSysId,buildings);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{buildingSysId}/{modifiedUserId}")
    public ResponseEntity<ApiResponseMessage> daleteBuilding(
            @PathVariable int buildingSysId,
            @PathVariable int modifiedUserId
    ){
        buildingsDAO.deleteBuilding(buildingSysId,modifiedUserId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Building is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @PostMapping("/checkData")
    public ResponseEntity<Boolean> checkData(@RequestBody Buildings buildings){
       Boolean a = buildingsDAO.checkData(buildings.getName(), buildings.getNumber());
        return new ResponseEntity<>(a,HttpStatus.OK);
    }
}
