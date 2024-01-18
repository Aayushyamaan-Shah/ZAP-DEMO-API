package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.GroupsDAO;
import in.teamelementals.zap.zap_attendance_system.model.ApiResponseMessage;
import in.teamelementals.zap.zap_attendance_system.model.Groups;
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
@RequestMapping(path = "/zap/api/groups")
public class GroupsController {

    @Autowired
    private GroupsDAO groupsDAO;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addGroup(@RequestBody Groups groups){
        Boolean a = groupsDAO.addGroup(groups);
        return new ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Groups>> getAll(){
        List<Groups> groupsList = groupsDAO.getAll();
        return new ResponseEntity<>(groupsList,HttpStatus.OK);
    }

    @PutMapping("/update/{groupsSysid}")
    public ResponseEntity<Boolean> updateGroup(
            @PathVariable int groupsSysid,
            @RequestBody Groups groups
    ){
       Boolean a = groupsDAO.updateGroup(groupsSysid,groups);
        return new ResponseEntity<>(a,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{groupsSysid}/{modifiedUserId}")
    public ResponseEntity<ApiResponseMessage> deleteGroup(
            @PathVariable int groupsSysid,
            @PathVariable int modifiedUserId){
        groupsDAO.deleteGroup(groupsSysid, modifiedUserId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Group is deleted successfully")
                .success(true)
                .Status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

}
