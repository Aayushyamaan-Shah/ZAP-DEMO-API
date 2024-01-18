package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.FacultyRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.RoleRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.UserRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.FacultyDAO;
import in.teamelementals.zap.zap_attendance_system.dao.UserDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.helper.Helper;
import in.teamelementals.zap.zap_attendance_system.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class FacultyPostgresImpl implements FacultyDAO{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleRepositories roleRepositories;

    @Value("${faculty.role.id}")
    private String facultyRoleId;

    @Value("${admin.role.id}")
    private String adminRoleId;

    @Autowired
    private FacultyRepositories facultyRepositories;

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public Integer insert(FacultyUserCustom facultyUserCustom){
        Boolean a = userRepositories.existsByEnrollmentNoAndSysStatusOrEmailAndSysStatus(facultyUserCustom.getUser().getEnrollmentNo(),"A",facultyUserCustom.getUser().getEmail(),"A");
        if(a){
            return 0;
        }
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        //Role Setup
        Role role = roleRepositories.findById(facultyRoleId).get();
        facultyUserCustom.getUser().setRole("ROLE_FACULTY");
        facultyUserCustom.getUser().getRoles().add(role);

       User user = userDAO.addUser(facultyUserCustom.getUser());
       Faculty faculty = new Faculty();
       faculty.setFacultySysid(user.getUserId());
       faculty.setCreatedDate(ts);
       faculty.setCreatorId(facultyUserCustom.getCreatorId());
       faculty.setSysStatus(facultyUserCustom.getSysStatus());
       faculty.setProgramType(facultyUserCustom.getProgramType());
       faculty.setInstitute(facultyUserCustom.getInstitute());
       faculty.setDateOfJoining(facultyUserCustom.getDateOfJoining());
       faculty.setDepartment(facultyUserCustom.getDepartment());
       faculty.setDesignation(facultyUserCustom.getDesignation());
       faculty.setReportingAuthority(facultyUserCustom.getReportingAuthority());

       facultyRepositories.save(faculty);
       return 1;
    }

    @Override
    public Integer update(int facultySysid, FacultyUserCustom facultyUserCustom){

        Boolean a = userRepositories.existsByEnrollmentNoAndUserIdIsNotAndSysStatusOrEmailAndUserIdIsNotAndSysStatus(facultyUserCustom.getUser().getEnrollmentNo(),facultySysid,"A",facultyUserCustom.getUser().getEmail(),facultySysid,"A");
        if (a){
            return 0;
        }
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        userDAO.updateUserByID(facultySysid,facultyUserCustom.getUser());
        Faculty faculty = facultyRepositories.findById(facultySysid).orElseThrow(()->new ResourceNotFoundException("Faculty not found!!"));
        faculty.setModifiedDate(ts);
        faculty.setModifiedUserid(facultyUserCustom.getModifiedUserid());
        faculty.setSysStatus(facultyUserCustom.getSysStatus());
        faculty.setProgramType(facultyUserCustom.getProgramType());
        faculty.setInstitute(facultyUserCustom.getInstitute());
        faculty.setDateOfJoining(facultyUserCustom.getDateOfJoining());
        faculty.setDepartment(facultyUserCustom.getDepartment());
        faculty.setDesignation(facultyUserCustom.getDesignation());
        faculty.setReportingAuthority(facultyUserCustom.getReportingAuthority());
        facultyRepositories.save(faculty);
        return 1;
    }

    @Override
    public void delete(int facultySysid, int modifierId){
        userDAO.deleteUserByID(facultySysid,modifierId);
        Faculty faculty = facultyRepositories.findById(facultySysid).orElseThrow(()->new ResourceNotFoundException("Faculty not found!!"));
        faculty.setModifiedUserid(modifierId);
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        faculty.setModifiedDate(ts);
        faculty.setSysStatus("D");
        facultyRepositories.save(faculty);
    }

    @Override
    public FacultyUserCustom getFaculty(int facultySysid){

        Faculty faculty = facultyRepositories.findById(facultySysid).orElseThrow(()->new ResourceNotFoundException("Faculty not found!!"));
        User user = userRepositories.findById(facultySysid).orElseThrow(()->new ResourceNotFoundException("User not found"));
        FacultyUserCustom facultyUserCustom = new FacultyUserCustom();
        facultyUserCustom.setUser(user);
        facultyUserCustom.setFacultySysid(faculty.getFacultySysid());
        facultyUserCustom.setCreatedDate(faculty.getCreatedDate());
        facultyUserCustom.setCreatorId(faculty.getCreatorId());
        facultyUserCustom.setModifiedDate(faculty.getModifiedDate());
        facultyUserCustom.setModifiedUserid(faculty.getModifiedUserid());
        facultyUserCustom.setSysStatus(faculty.getSysStatus());
        facultyUserCustom.setProgramType(faculty.getProgramType());
        facultyUserCustom.setInstitute(faculty.getInstitute());
        facultyUserCustom.setDateOfJoining(faculty.getDateOfJoining());
        facultyUserCustom.setDepartment(faculty.getDepartment());
        facultyUserCustom.setDesignation(faculty.getDesignation());
        facultyUserCustom.setReportingAuthority(faculty.getReportingAuthority());
        return facultyUserCustom;
    }

    @Override
    public PageableResponse<FacultyUserCustom> getAllFaculty(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        List<FacultyUserCustom> facultyUserCustomList = new ArrayList<>();
        List<Faculty> facultyList = facultyRepositories.findBySysStatus("A");
        for (Faculty faculty:facultyList){
            User user = userRepositories.findById(faculty.getFacultySysid()).orElseThrow(()->new ResourceNotFoundException("User not found"));
            FacultyUserCustom facultyUserCustom = new FacultyUserCustom();
            facultyUserCustom.setUser(user);
            facultyUserCustom.setFacultySysid(faculty.getFacultySysid());
            facultyUserCustom.setCreatedDate(faculty.getCreatedDate());
            facultyUserCustom.setCreatorId(faculty.getCreatorId());
            facultyUserCustom.setModifiedDate(faculty.getModifiedDate());
            facultyUserCustom.setModifiedUserid(faculty.getModifiedUserid());
            facultyUserCustom.setSysStatus(faculty.getSysStatus());
            facultyUserCustom.setProgramType(faculty.getProgramType());
            facultyUserCustom.setInstitute(faculty.getInstitute());
            facultyUserCustom.setDateOfJoining(faculty.getDateOfJoining());
            facultyUserCustom.setDepartment(faculty.getDepartment());
            facultyUserCustom.setDesignation(faculty.getDesignation());
            facultyUserCustom.setReportingAuthority(faculty.getReportingAuthority());
            facultyUserCustomList.add(facultyUserCustom);
        }

        Page<FacultyUserCustom> page = new PageImpl<FacultyUserCustom>(facultyUserCustomList,pageable,facultyUserCustomList.size());

        return Helper.getPageableResponse(page,FacultyUserCustom.class);
    }
}
