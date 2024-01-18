package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewwriterdevicesRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewwriterdevices;
import org.springframework.web.bind.annotation.RequestBody;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') || hasRole('FACULTY') || hasRole('HR') || hasRole('LIBRARIAN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewWriterDevices")
public class viewwriterdevicesController {

    @Autowired
    private viewwriterdevicesRepositories viewwriterdevicesRepositories1;

    @GetMapping
    public ResponseEntity<List<viewwriterdevices>> getAll(){
        List<viewwriterdevices> viewwriterdevicesList = viewwriterdevicesRepositories1.findAll();
        return new ResponseEntity<>(viewwriterdevicesList, HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<List<viewwriterdevices>> getByRole(@RequestBody String role){
        String processRole = new JSONObject(role).getString("role"); 
        List<viewwriterdevices> viewwriterdevicesList = viewwriterdevicesRepositories1.findByAccessibleRole(processRole);
        return new ResponseEntity<>(viewwriterdevicesList, HttpStatus.OK);
    }

}
