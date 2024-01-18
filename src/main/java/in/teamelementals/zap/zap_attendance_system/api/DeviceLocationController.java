package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.DeviceLocationDao;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.DeviceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') || hasRole('HR')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/deviceLocation")
public class DeviceLocationController {

    @Autowired
    private DeviceLocationDao deviceLocationDao;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addDeviceLocation(@RequestBody DeviceLocation deviceLocation){
       Boolean a = deviceLocationDao.addDeviceLocation(deviceLocation);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<HashMap<Integer,Boolean>> addDeviceLocationList(@RequestBody List<DeviceLocation> deviceLocationList){
        HashMap<Integer,Boolean> hashMap = deviceLocationDao.addDeviceLocationList(deviceLocationList);
        return new ResponseEntity<>(hashMap, HttpStatus.CREATED);
    }

    @GetMapping("/getByLocation/{locationId}")
    public ResponseEntity<List<DeviceLocation>> getDeviceLocationByLocation(@PathVariable int locationId){
        List<DeviceLocation> deviceLocationList = deviceLocationDao.getDeviceLocationByLocation(locationId);
        return new ResponseEntity<>(deviceLocationList,HttpStatus.OK);
    }

    @PutMapping("/update/{deviceLocationId}")
    public ResponseEntity<Boolean> updateDeviceLocation(
            @PathVariable int deviceLocationId,
            @RequestBody DeviceLocation deviceLocation
    ){
       Boolean a = deviceLocationDao.updateDeviceLocation(deviceLocationId,deviceLocation);
        return new ResponseEntity<>(a,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{deviceLocationId}/{modifiedUserId}")
    public ResponseEntity<ApiResponseMessage> deleteDeviceLocation(
            @PathVariable int deviceLocationId,
            @PathVariable int modifiedUserId
    ){
        deviceLocationDao.deleteDeviceLocation(deviceLocationId,modifiedUserId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Device is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

}
