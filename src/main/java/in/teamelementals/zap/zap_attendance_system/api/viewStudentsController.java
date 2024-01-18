package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewStudentsRepositories;
import in.teamelementals.zap.zap_attendance_system.helper.Helper;
import in.teamelementals.zap.zap_attendance_system.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') || hasRole('FACULTY') || hasRole('HR') || hasRole('LIBRARIAN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewStudents")
public class viewStudentsController{

    @Autowired
    private viewStudentsRepositories viewStudentsRepositories;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<Student>> getStudents(
            @RequestParam(name = "enrollmentNo",defaultValue = "%",required = false) String enrollmentNo,
            @RequestParam(name = "name",defaultValue = "%",required = false) String name,
            @RequestParam(name = "institute",defaultValue = "%",required = false) String institute,
            @RequestParam(name = "degree",defaultValue = "%",required = false) String degree,
            @RequestParam(name = "currentSemester1",defaultValue = "0",required = false) int currentSemester1,
            @RequestParam(name = "currentSemester2",defaultValue = "10",required = false) int currentSemester2,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "userSysid",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(--pageNumber,pageSize,sort);

        Page<viewStudents> viewStudentsList = viewStudentsRepositories.findByEnrollmentNoLikeAndNameLikeAndInstituteLikeAndDegreeLikeAndCurrentSemesterBetween("%"+enrollmentNo.toUpperCase()+"%","%"+name.toUpperCase()+"%","%"+institute.toUpperCase()+"%","%"+degree.toUpperCase()+"%",currentSemester1,currentSemester2,pageable);

        List<Student> studentList = new ArrayList<>();
        for (viewStudents viewStudents:viewStudentsList){
            Student student = new Student();
            student.setStudentId(viewStudents.getStudentSysid());
            student.setCreatedDate(viewStudents.getStudentCreatedDate());
            student.setSysStatus(viewStudents.getSysStatus());
            student.setCreatorId(viewStudents.getCreatorId());
            student.setModifiedDate(viewStudents.getStudentModifiedDate());
            student.setModifierId(viewStudents.getModifiedUserid());
            student.setInstitute(viewStudents.getInstitute());
            student.setProgramType(viewStudents.getProgramType());
            student.setAdmissionSemester(viewStudents.getAdmissionSemester());
            student.setDegree(viewStudents.getDegree());
            student.setCurrentSemester(viewStudents.getCurrentSemester());
            student.setDivision(viewStudents.getDivision());
            student.setBatch(viewStudents.getBatch());
            student.setAdmissionSemester(viewStudents.getAdmissionSemester());
            student.setDateOfJoining(viewStudents.getDateOfJoining());
            User user = new User();
            user.setUserId(viewStudents.getUserSysid());
            user.setCreatedDate(viewStudents.getCreatedDate());
            user.setSysStatus(viewStudents.getSysStatus());
            user.setCreatorId(viewStudents.getCreatorId());
            user.setModifiedDate(viewStudents.getModifiedDate());
            user.setModifierId(viewStudents.getModifiedUserid());
            user.setName(viewStudents.getName());
            user.setContact(Long.valueOf(viewStudents.getContact()));
            user.setEmail(viewStudents.getEmail());
            user.setRole(viewStudents.getRole());
            user.setPassword(viewStudents.getPasswordHash());
            user.setSalt(viewStudents.getSalt());
            user.setAccountStatus(viewStudents.getAccountStatus());
            user.setEnrollmentNo(viewStudents.getEnrollmentNo());
            student.setUser(user);
            studentList.add(student);
        }
        Page<Student> pages = new PageImpl<Student>(studentList, pageable, studentList.size());
        return new ResponseEntity<>(Helper.getPageableResponse(pages,Student.class), HttpStatus.OK);
    }
}
