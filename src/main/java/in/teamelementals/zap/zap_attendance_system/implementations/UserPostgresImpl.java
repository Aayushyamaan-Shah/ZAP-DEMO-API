package in.teamelementals.zap.zap_attendance_system.implementations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.teamelementals.zap.zap_attendance_system.OtherServices.OtpService;
import in.teamelementals.zap.zap_attendance_system.Repositories.RoleRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.UserRepositories;
import in.teamelementals.zap.zap_attendance_system.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import in.teamelementals.zap.zap_attendance_system.dao.UserDAO;
import in.teamelementals.zap.zap_attendance_system.model.User;
import org.springframework.stereotype.Service;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class UserPostgresImpl implements UserDAO{

    @Value("${faculty.role.id}")
    private String facultyRoleId;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private JavaMailSender javaMailSender;

    OtpService otpService = new OtpService();

    @Override
    public Integer deleteUserByID(Integer id,int modifierId){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        User user = userRepositories.findById(id).orElseThrow(()-> new RuntimeException("User Not Found"));
        user.setSysStatus("D");
        user.setModifierId(modifierId);
        user.setModifiedDate(ts);
        userRepositories.save(user);
        return 1;
    }

    //Api for student object
    @Override
    public User addUser(User user){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        user.setCreatedDate(ts);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userRepositories.save(user);
        return user1;
    }

    @Override
    public Integer insertUser(User user, boolean returnType) {

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        user.setCreatedDate(ts);
        userRepositories.save(user);
        return 1;
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        String sql = """
//        INSERT INTO users(
//            created_date,
//            creator_id,
//            sys_status,
//            account_status,
//            name,
//            contact,
//            email,
//            role,
//            password_hash,
//            salt
//        )
//        values
//        (now(), ?, ?, ?, ?, ?, ?, ?, ?, ?)
//        """;
//
//        int jdbcReturnStatus = jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection
//              .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//              ps.setInt(1,user.getCreatorId());
//              ps.setString(2,user.getSysStatus());
//              ps.setString(3,user.getAccountStatus());
//              ps.setString(4,user.getName());
//              ps.setLong(5,user.getContact());
//              ps.setString(6,user.getEmail());
//              ps.setString(7,user.getRole());
//              ps.setString(8,user.getPassword());
//              ps.setString(9,user.getSalt());
//
//              return ps;
//            },keyHolder);
//
//        if(returnType){
//            try{
//                return Integer.parseInt(keyHolder.getKeyList().get(0).toString().split(",")[0].split("=")[1]);
//            }catch(InvalidDataAccessApiUsageException | NullPointerException e){
//                Logger.getLogger(TESConstants.LOGGER_NAME).warning("User was not inserted.");
//                Logger.getLogger(TESConstants.LOGGER_NAME).warning(e.getMessage());
//            }
//        }
//
//        return jdbcReturnStatus;

    }

    @Override
    public String resetOtp(String userId) {
        User user = userRepositories.findByEnrollmentNo(userId.toUpperCase());

        String otp = otpService.generateRandomOTP();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail(),"a5.theelementalsquad@gmail.com");
        mailMessage.setSubject("One Time Password For Your Account");
        mailMessage.setText("Hey "+user.getName()+", \n\n" +
                "Your OTP for Reset Password is "+ otp
        );
        mailMessage.setFrom("noreply@teamelementals.in");
        javaMailSender.send(mailMessage);
        return otp;
    }

    @Override
    public Integer resetPassword(String userId, String password){
       User user = userRepositories.findByEnrollmentNo(userId.toUpperCase());
       user.setPassword(passwordEncoder.encode(password));
       userRepositories.save(user);
       return 1;
    }

    @Override
    public List<User> selectAllUsers(Integer page) {
        return new ArrayList<>();
    }

    @Override
    public User selectUserByID(Integer id) {
        return new User();
    }

    @Override
    public List<User> selectUserByName(String name, Integer page) {
        return new ArrayList<>();
    }

    @Override
    public Integer updateUserByID(Integer id, User user) {
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());

        User user1 = userRepositories.findById(id).orElseThrow(()->new RuntimeException("User not Found"));
        user1.setModifiedDate(ts);
        user1.setModifierId(user.getModifierId());
        user1.setAccountStatus(user.getAccountStatus());
        user1.setName(user.getName());
        user1.setContact(user.getContact());
        user1.setEmail(user.getEmail());
        user1.setRole(user.getRole());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setSalt(user.getSalt());
        user1.setEnrollmentNo(user.getEnrollmentNo());

        userRepositories.save(user1);
        return 1;
    }

    @Override
    public Integer updateUserByID(User user) {
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());

        User user1 = userRepositories.findById(user.getUserId()).orElseThrow(()->new RuntimeException("User not Found"));
        user1.setModifiedDate(ts);
        user1.setModifierId(user.getModifierId());
        user1.setAccountStatus(user.getAccountStatus());
        user1.setName(user.getName());
        user1.setContact(user.getContact());
        user1.setEmail(user.getEmail());
        user1.setRole(user.getRole());
        user1.setPassword(user.getPassword());
        user1.setSalt(user.getSalt());
        user1.setEnrollmentNo(user.getEnrollmentNo());

        userRepositories.save(user1);
        return 1;
    }

    
}