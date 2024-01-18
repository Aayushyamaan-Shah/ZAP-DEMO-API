package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.FacultyUserCustom;
import in.teamelementals.zap.zap_attendance_system.model.PageableResponse;

public interface FacultyDAO {

    Integer insert(FacultyUserCustom facultyUserCustom);

    Integer update(int facultySysid,FacultyUserCustom facultyUserCustom);

    void delete(int facultySysid,int modifierId);

    FacultyUserCustom getFaculty(int facultySysid);

    PageableResponse<FacultyUserCustom> getAllFaculty(int pageNumber,int pageSize,String sortBy,String sortDir);
}
