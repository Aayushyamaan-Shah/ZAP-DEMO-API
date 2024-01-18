package in.teamelementals.zap.zap_attendance_system.dao;
import in.teamelementals.zap.zap_attendance_system.model.Department;
import java.util.List;

public interface DepartmentDAO{

    void addDepartment(Department department);

    List<Department> getDepartmentByInstitute(int instituteSysid);

    void updateDepartment(int departmentSysid,Department department);

    void deleteDepartment(int departmentSysid);



}
