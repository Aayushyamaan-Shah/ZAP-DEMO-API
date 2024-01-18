package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.LocationDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.Location;
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
@RequestMapping(path = "/zap/api/location")
public class LocationController{

    @Autowired
    private LocationDAO locationDAO;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addLocation(@RequestBody Location location){
        Boolean a =  locationDAO.addLocation(location);
         return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Location>> getAll(){
        List<Location> locations = locationDAO.getAll();
        return new ResponseEntity<>(locations,HttpStatus.OK);
    }

    @GetMapping("getByFloor/{building}/{floor}")
    public ResponseEntity<List<Location>> getByFloorAndBuilding(
            @PathVariable int floor,
            @PathVariable int building
    ){
        List<Location> locationList = locationDAO.getByFloorAndBuilding(floor,building);
        return new ResponseEntity<>(locationList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{locationSysid}/{modifiedUserId}")
    public ResponseEntity<ApiResponseMessage> deleteLocation(
            @PathVariable int locationSysid,
            @PathVariable int modifiedUserId
    ){
        locationDAO.deleteLocation(locationSysid,modifiedUserId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Location is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @PutMapping("/update/{locationSysid}")
    public ResponseEntity<Boolean> updateLocation(
            @PathVariable int locationSysid,
            @RequestBody Location location
    ){
       Boolean a= locationDAO.updateLocation(locationSysid,location);
       return new ResponseEntity<>(a,HttpStatus.OK);
    }
}
