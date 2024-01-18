package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.Institute;

import java.util.List;

public interface InstituteDAO {

    int addInstitute(Institute institute);

    List<Institute> getInstitutes(String sysStatus);

    void updateInstitute(int instituteSysid,Institute institute);

    void deleteInstitute(int instituteSysid);
}
