package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.model.DeviceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import in.teamelementals.zap.zap_attendance_system.dao.DeviceDAO;
import in.teamelementals.zap.zap_attendance_system.model.Device;

@CrossOrigin
@Controller
@PreAuthorize("hasRole('ADMIN') || hasRole('HR')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/device")
public class DeviceController{

  @Autowired
  private DeviceDAO deviceDAO;

  @PostMapping("/add")
  public ResponseEntity<DeviceResponse> addDevice(@RequestBody Device device,
     @RequestParam String deviceRole
  ){
    DeviceResponse response = deviceDAO.addDevice(device,deviceRole);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{deviceId}")
  public ResponseEntity<Integer> updateDeleted(
          @PathVariable int deviceId,
          @RequestBody Device device
  ){
    deviceDAO.updateDevice(deviceId,device);
    return new ResponseEntity<>(1,HttpStatus.OK);
  }

  @DeleteMapping("/delete/{deviceId}")
  public ResponseEntity<Integer> deleteDevice(@PathVariable int deviceId){
       deviceDAO.deleteDevice(deviceId);
    return new ResponseEntity<>(1,HttpStatus.OK);
  }
}
