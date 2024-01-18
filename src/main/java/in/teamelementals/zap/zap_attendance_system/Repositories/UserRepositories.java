package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User,Integer> {

    User findByEnrollmentNo(String enrollmentNo);

    Boolean existsByEnrollmentNoAndSysStatusOrEmailAndSysStatus(String enrollmentNo,String sysStatus,String email,String sysStatus1);

    Boolean existsByEnrollmentNoAndUserIdIsNotAndSysStatusOrEmailAndUserIdIsNotAndSysStatus(String enrollmentNo,int userId,String sysStatus,String email,int userId1,String sysStatus1);

}
