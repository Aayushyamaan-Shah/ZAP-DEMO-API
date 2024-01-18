package in.teamelementals.zap.zap_attendance_system.api;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import in.teamelementals.zap.zap_attendance_system.model.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import in.teamelementals.zap.zap_attendance_system.dao.StudentDAO;
import in.teamelementals.zap.zap_attendance_system.model.Student;

@CrossOrigin
@Controller
@PreAuthorize("hasRole('ADMIN') || hasRole('LIBRARIAN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/student")
public class StudentController{

    @Autowired 
    private StudentDAO studentDAO;

    @PostMapping(path="/add")
    public @ResponseBody Integer addNewStudent (
        @RequestBody Student student) {
        return studentDAO.insertStudent(student);
    }

    @PostMapping(path="/addList")
    public @ResponseBody HashMap<String, String> addStudentList (
        @RequestBody List<Student> students) {
        return studentDAO.insertListOfStudents(students);
    }

    @PutMapping(path="/update/{roll-no}")
    public @ResponseBody Integer updateStudentByRollNo (@PathVariable("roll-no") String rollNo, @RequestBody Student student) {
      return studentDAO.updateStudentByRollNo(rollNo, student);
    }

    @PutMapping(path="/update")
    public @ResponseBody Integer updateuserById (@RequestBody Student student) {
        return studentDAO.updateStudentByRollNo(student);
    }

    @GetMapping(path="/all")
    public @ResponseBody PageableResponse<Student> getAllStudents(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "studentId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
      return studentDAO.selectAllStudent(pageNumber, pageSize, sortBy, sortDir);

    }

    @GetMapping(path="/id/{id}")
    public @ResponseBody List<Student> getStudentByID(@PathVariable("id") Integer id){
        return studentDAO.selectStudentByID(id);
    }

    @GetMapping(path="/roll-no/{roll-no}")
    public @ResponseBody Student getStudentByRollNo(@PathVariable("roll-no") String rollNo){
        return studentDAO.selectStudentByRollNo(rollNo);
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody Integer deleteByRollNo(@RequestBody Student student){
        return studentDAO.deleteStudentByRollNo(student);
    }

    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody Integer deleteByRollNo(@PathVariable("id") String id){
        return studentDAO.deleteStudentByRollNo(id);
    }

    @GetMapping(path={"/name/{name}","/name/{name}/{page}"})
    public @ResponseBody List<Student> getStudentByName(@PathVariable String name, @PathVariable Optional<Integer> page){
        return studentDAO.selectStudentByName(name, page.orElse(-1));
    }
}