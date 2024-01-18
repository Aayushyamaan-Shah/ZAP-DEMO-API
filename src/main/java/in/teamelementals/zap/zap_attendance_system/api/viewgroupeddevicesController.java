package in.teamelementals.zap.zap_attendance_system.api;


import in.teamelementals.zap.zap_attendance_system.Repositories.viewgroupeddevicesRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewgroupeddevices;
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
@RequestMapping(path = "/zap/api/viewgroupeddevices")
public class viewgroupeddevicesController{

    @Autowired
    private  viewgroupeddevicesRepositories viewgroupeddevicesRepositories;

    @GetMapping("/getByGroup/{groupId}")
    public ResponseEntity<List<viewgroupeddevices>> getByGroupId(@PathVariable int groupId){
        List<viewgroupeddevices> viewgroupeddevicesList = viewgroupeddevicesRepositories.findByGroupId(groupId);
        return new ResponseEntity<>(viewgroupeddevicesList, HttpStatus.OK);
    }
}
