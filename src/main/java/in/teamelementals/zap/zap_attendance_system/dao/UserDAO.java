package in.teamelementals.zap.zap_attendance_system.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import in.teamelementals.zap.zap_attendance_system.model.User;

public interface UserDAO {

    List<User> selectAllUsers(Integer page);

    User addUser(User user);

    User selectUserByID(Integer id);

    List<User> selectUserByName(String name, Integer page);

    Integer deleteUserByID(Integer id,int modifierId);

    Integer updateUserByID(Integer id, User user);

    Integer updateUserByID(User user);

    Integer insertUser(@JsonProperty("user") User user, boolean returnType);

    String resetOtp(String userId);

    Integer resetPassword(String userId,String password);

}