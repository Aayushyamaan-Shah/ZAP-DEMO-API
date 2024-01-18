package in.teamelementals.zap.zap_attendance_system;

import java.util.logging.Logger;
import in.teamelementals.zap.zap_attendance_system.OtherServices.AttendanceUpload;
import in.teamelementals.zap.zap_attendance_system.Repositories.RoleRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.viewmainattendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication()
@EnableWebMvc
@EnableScheduling
public class ZapAttendanceSystemApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepositories roleRepositories;

	@Autowired
	private AttendanceUpload attendanceUpload;

	@Autowired
	private viewmainattendanceRepositories viewmainattendanceRepositories;


	@Value("${student.role.id}")
	private String role_student_id;
	public static void main(String[] args) {


		SpringApplication.run(ZapAttendanceSystemApplication.class, args);
		Logger.getLogger("TES Logger").info("Application started.");
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			Role role_Student = Role.builder().roleId(role_student_id).roleName("ROLE_STUDENT").build();
			roleRepositories.save(role_Student);

		}catch (Exception e){
			e.printStackTrace();
		}
	}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
