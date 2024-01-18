package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.implementations.StatasticsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/statastics")
public class StatasticsController {

    @Autowired
    private StatasticsImpl statastics;

    @GetMapping("/inOut")
     public ResponseEntity<Integer> attendanceCount(){
       int count =  statastics.attendanceCount();
       return new ResponseEntity<>(count, HttpStatus.OK);
     }

}
