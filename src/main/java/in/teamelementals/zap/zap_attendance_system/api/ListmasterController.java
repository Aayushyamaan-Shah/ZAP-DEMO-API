package in.teamelementals.zap.zap_attendance_system.api;
import in.teamelementals.zap.zap_attendance_system.dao.ListmasterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/Dropdown")
public class ListmasterController{

    @Autowired
    private ListmasterDAO listmasterDAO;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/{parameter}")
    public ResponseEntity<List<String>> getListValueByParameter(@PathVariable String parameter){
       List<String> dropDownList = listmasterDAO.getListValueByParameter(parameter);
        return new ResponseEntity<>(dropDownList, HttpStatus.OK);
    }

    @PostMapping("/mail")
    public ResponseEntity<Integer> mailService(){

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("sales@teamelementalsin");
            mailMessage.setSubject("sample subject");
            mailMessage.setText("sample message");
            mailMessage.setFrom("noreply@teamelementals.in");
            javaMailSender.send(mailMessage);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
