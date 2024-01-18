package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewdevicelocationsRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewdevicelocations;
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
@RequestMapping(path = "/zap/api/viewdevice")
public class viewdevicelocationsController{

    @Autowired
    private viewdevicelocationsRepositories viewdevicelocationsRepositories;

    @GetMapping("/getByLocation/{locationId}")
    public ResponseEntity<List<viewdevicelocations>> getDeviceByLocation(@PathVariable int locationId){
        List<viewdevicelocations> viewdevicelocationsList = viewdevicelocationsRepositories.findByLocation(locationId);
        return new ResponseEntity<>(viewdevicelocationsList, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<viewdevicelocations>> getActiveDeviceLocation(){
       List<viewdevicelocations> viewdevicelocationsList = viewdevicelocationsRepositories.findByStatus("ONLINE".toUpperCase());
       return new ResponseEntity<>(viewdevicelocationsList,HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<viewdevicelocations>> getInactiveDeviceLocation(){
        List<viewdevicelocations> viewdevicelocationsList = viewdevicelocationsRepositories.findByStatus("OFFLINE".toUpperCase());
        return new ResponseEntity<>(viewdevicelocationsList,HttpStatus.OK);
    }

    @GetMapping("/getDevice")
    public ResponseEntity<List<viewdevicelocations>> getDeviceByMac(@RequestParam(name = "mac",defaultValue = "%",required = false) String mac){
        List<viewdevicelocations> viewdevicelocationsList = viewdevicelocationsRepositories.findByMacLikeAndLocation(mac.toUpperCase()+"%",-1);
        return new ResponseEntity<>(viewdevicelocationsList,HttpStatus.OK);
    }

    @GetMapping("/getHRDevice")
    public ResponseEntity<List<viewdevicelocations>> getHRDevice(){
        List<viewdevicelocations> viewdevicelocationsList = viewdevicelocationsRepositories.findByLocationName("%"+"HR"+"%");
        return new ResponseEntity<>(viewdevicelocationsList,HttpStatus.OK);
    }
}
