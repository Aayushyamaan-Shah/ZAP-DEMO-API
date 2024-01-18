package in.teamelementals.zap.zap_attendance_system.dao;

import java.util.List;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.teamelementals.zap.zap_attendance_system.model.PageableResponse;
import in.teamelementals.zap.zap_attendance_system.model.Student;

public interface StudentDAO {
    Integer insertStudent(@JsonProperty("student") Student user);

    PageableResponse<Student> selectAllStudent(int pageNumber, int pageSize, String sortBy, String sortDir);

    List<Student> selectStudentByID(Integer id);

    Student selectStudentByRollNo(String rollNo);

    List<Student> selectStudentByName(String name, Integer page);

    Integer deleteStudentByRollNo(String rollNo);

    Integer deleteStudentByRollNo(Student student);

    Integer updateStudentByRollNo(String rollNo, Student Student);

    Integer updateStudentByRollNo(Student student);

    HashMap<String, String> insertListOfStudents(@JsonProperty("students")List<Student> students); 

}
