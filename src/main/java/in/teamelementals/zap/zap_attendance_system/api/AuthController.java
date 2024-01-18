package in.teamelementals.zap.zap_attendance_system.api;
import in.teamelementals.zap.zap_attendance_system.exception.BadApiRequest;
import in.teamelementals.zap.zap_attendance_system.model.JwtRequest;
import in.teamelementals.zap.zap_attendance_system.model.JwtResponse;
import in.teamelementals.zap.zap_attendance_system.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/zap/api/auth")
public class AuthController{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEnrollmentNo(),request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEnrollmentNo());
        String token = this.helper.generateToken(userDetails);

//        User user = modelMapper.map(userDetails,User.class);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userDetails).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String enrollmentNo, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(enrollmentNo,password);
        try{
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new BadApiRequest("Invalid Username or Password !!");
        }
    }
}
