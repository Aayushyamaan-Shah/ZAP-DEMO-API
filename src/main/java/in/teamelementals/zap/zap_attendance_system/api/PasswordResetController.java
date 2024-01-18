package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/change")
public class PasswordResetController {

    @Autowired
    private UserDAO userDao;

    @GetMapping("/resetOtp/{userId}")
    public ResponseEntity<String> resetOtp(@PathVariable String userId){
        String otp = userDao.resetOtp(userId);
        return new ResponseEntity<>(otp, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Integer> resetPassword(@RequestParam String userId,@RequestParam String password,@RequestParam String key){
        if (key.equals("kjnfjdnvolnzolbfnlosnbfd")){
            int a =  userDao.resetPassword(userId,password);
            return new ResponseEntity<>(a,HttpStatus.OK);
        }
        return new ResponseEntity<>(0,HttpStatus.OK);
    }
}
