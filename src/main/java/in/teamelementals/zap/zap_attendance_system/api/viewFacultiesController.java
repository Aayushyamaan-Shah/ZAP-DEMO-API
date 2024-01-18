package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewFacultiesRepositories;
import in.teamelementals.zap.zap_attendance_system.helper.Helper;
import in.teamelementals.zap.zap_attendance_system.model.*;

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
@PreAuthorize("hasRole('ADMIN') || hasRole('HR') || hasRole('FACULTY')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewFaculties")
public class viewFacultiesController {

    @Autowired
    private viewFacultiesRepositories viewFacultiesRepositories;

    private FacultyUserCustom convertToContactDto(final viewFaculties faculty) {

        FacultyUserCustom facultyUserCustom = new FacultyUserCustom();
            facultyUserCustom.setFacultySysid(faculty.getFacultySysid());
            facultyUserCustom.setCreatedDate(faculty.getFacultyCreatedDate());
            facultyUserCustom.setCreatorId(faculty.getCreatorId());
            facultyUserCustom.setModifiedDate(faculty.getFacultyModifiedDate());
            facultyUserCustom.setModifiedUserid(faculty.getFacultyModifiedUserid());
            facultyUserCustom.setSysStatus(faculty.getFacultySysStatus());
            facultyUserCustom.setProgramType(faculty.getProgramType());
            facultyUserCustom.setInstitute(faculty.getInstitute());
            facultyUserCustom.setDateOfJoining(faculty.getDateOfJoining());
            facultyUserCustom.setDepartment(faculty.getDepartment());
            facultyUserCustom.setDesignation(faculty.getDesignation());
            facultyUserCustom.setReportingAuthority(faculty.getReportingAuthority());
            facultyUserCustom.setInstituteName(faculty.getInstituteName());

            User user = new User();
            user.setUserId(faculty.getUserSysid());
            user.setCreatedDate(faculty.getCreatedDate());
            user.setSysStatus(faculty.getSysStatus());
            user.setCreatorId(faculty.getCreatorId());
            user.setModifiedDate(faculty.getModifiedDate());
            user.setModifierId(faculty.getModifiedUserid());
            user.setName(faculty.getName());
            user.setContact(Long.valueOf(faculty.getContact()));
            user.setEmail(faculty.getEmail());
            user.setRole(faculty.getRole());
            user.setPassword(faculty.getPasswordHash());
            user.setSalt(faculty.getSalt());
            user.setAccountStatus(faculty.getAccountStatus());
            user.setEnrollmentNo(faculty.getEnrollmentNo());

            facultyUserCustom.setUser(user);

        return facultyUserCustom;
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<FacultyUserCustom>> getFaculties(
            @RequestParam(name = "enrollmentNo",defaultValue = "%",required = false) String enrollmentNo,
            @RequestParam(name = "name",defaultValue = "%",required = false) String name,
            @RequestParam(name = "institute",defaultValue = "0",required = false) int institute,
            @RequestParam(name = "department",defaultValue = "%",required = false) String department,
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "userSysid",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        if (pageNumber<1){
            pageNumber=1;
        }
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(--pageNumber,pageSize,sort);

        Page<viewFaculties> viewFacultiesList;

        if (institute==0){
            viewFacultiesList = viewFacultiesRepositories.findByEnrollmentNoLikeAndNameLikeAndDepartmentLike("%"+enrollmentNo.toUpperCase()+"%","%"+name.toUpperCase()+"%","%"+department.toUpperCase()+"%",pageable);
        }
        else {
            viewFacultiesList = viewFacultiesRepositories.findByEnrollmentNoLikeAndNameLikeAndInstituteAndDepartmentLike("%" + enrollmentNo.toUpperCase() + "%", "%" + name.toUpperCase() + "%", institute, "%" + department.toUpperCase() + "%",pageable);
        }
        List<FacultyUserCustom> facultyUserCustomList = new ArrayList<>();
        for (viewFaculties viewFaculties:viewFacultiesList){
            FacultyUserCustom facultyUserCustom = new FacultyUserCustom();
            facultyUserCustom.setFacultySysid(viewFaculties.getFacultySysid());
            facultyUserCustom.setCreatedDate(viewFaculties.getFacultyCreatedDate());
            facultyUserCustom.setCreatorId(viewFaculties.getCreatorId());
            facultyUserCustom.setModifiedDate(viewFaculties.getFacultyModifiedDate());
            facultyUserCustom.setModifiedUserid(viewFaculties.getFacultyModifiedUserid());
            facultyUserCustom.setSysStatus(viewFaculties.getFacultySysStatus());
            facultyUserCustom.setProgramType(viewFaculties.getProgramType());
            facultyUserCustom.setInstitute(viewFaculties.getInstitute());
            facultyUserCustom.setDateOfJoining(viewFaculties.getDateOfJoining());
            facultyUserCustom.setDepartment(viewFaculties.getDepartment());
            facultyUserCustom.setDesignation(viewFaculties.getDesignation());
            facultyUserCustom.setReportingAuthority(viewFaculties.getReportingAuthority());
            facultyUserCustom.setInstituteName(viewFaculties.getInstituteName());

            User user = new User();
            user.setUserId(viewFaculties.getUserSysid());
            user.setCreatedDate(viewFaculties.getCreatedDate());
            user.setSysStatus(viewFaculties.getSysStatus());
            user.setCreatorId(viewFaculties.getCreatorId());
            user.setModifiedDate(viewFaculties.getModifiedDate());
            user.setModifierId(viewFaculties.getModifiedUserid());
            user.setName(viewFaculties.getName());
            user.setContact(Long.valueOf(viewFaculties.getContact()));
            user.setEmail(viewFaculties.getEmail());
            user.setRole(viewFaculties.getRole());
            user.setPassword(viewFaculties.getPasswordHash());
            user.setSalt(viewFaculties.getSalt());
            user.setAccountStatus(viewFaculties.getAccountStatus());
            user.setEnrollmentNo(viewFaculties.getEnrollmentNo());

            facultyUserCustom.setUser(user);
            facultyUserCustomList.add(facultyUserCustom);
        }

        // Page<FacultyUserCustom> pages = new PageImpl<FacultyUserCustom>(facultyUserCustomList, pageable, facultyUserCustomList.size());
        Page<FacultyUserCustom> pages = viewFacultiesList.map(this::convertToContactDto);
        // BeanUtils.copyProperties(pages, viewFacultiesList);

        return new ResponseEntity<>(Helper.getPageableResponse(pages,FacultyUserCustom.class), HttpStatus.OK);
    }
}
