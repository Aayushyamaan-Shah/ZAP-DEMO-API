package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.GroupedDevicesDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.GroupedDevices;
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
@RequestMapping(path = "/zap/api/groupedDevices")
public class GroupedDevicesController{
    @Autowired
    private GroupedDevicesDAO groupedDevicesDAO;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addDevice(@RequestBody GroupedDevices groupedDevices){
        Boolean a = groupedDevicesDAO.addDevice(groupedDevices);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @GetMapping("/getByGroup/{group}")
    public ResponseEntity<List<GroupedDevices>> getByGroup(@PathVariable("group") int groupId){
        List<GroupedDevices> groupedDevicesList = groupedDevicesDAO.getByGroup(groupId);
        return new ResponseEntity<>(groupedDevicesList,HttpStatus.OK);
    }

    @PutMapping("/update/{groupedDevicesSysid}")
    public ResponseEntity<Boolean> updateGroupedDevice(
            @PathVariable int groupedDevicesSysid,
            @RequestBody GroupedDevices groupedDevices){
      Boolean a =  groupedDevicesDAO.updateDevice(groupedDevicesSysid,groupedDevices);
        return new ResponseEntity<>(a,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{groupedDevicesSysid}/{modifiedUserId}")
    public ResponseEntity<ApiResponseMessage> deleteGroupedDevice(
            @PathVariable int groupedDevicesSysid,
            @PathVariable int modifiedUserId
    ){
        groupedDevicesDAO.deleteDevice(groupedDevicesSysid,modifiedUserId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("GropedDevice is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
}
