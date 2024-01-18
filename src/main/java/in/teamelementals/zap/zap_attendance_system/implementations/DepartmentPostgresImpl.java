package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.DepartmentRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.DepartmentDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class DepartmentPostgresImpl implements DepartmentDAO {

    @Autowired
    private DepartmentRepositories departmentRepositories;
    @Override
    public void addDepartment(Department department){
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        department.setCreatedDate(ts);
        departmentRepositories.save(department);
    }

    @Override
    public List<Department> getDepartmentByInstitute(int instituteSysid){
        List<Department> departmentList = departmentRepositories.findByInstitute(instituteSysid);
        return departmentList;
    }

    @Override
    public void updateDepartment(int departmentSysid, Department department) {
        Department department1 = departmentRepositories.findById(departmentSysid).orElseThrow(()->new ResourceNotFoundException("Department not found"));
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        department1.setModifiedDate(ts);
        department1.setModifiedUserid(department.getModifiedUserid());
        department1.setName(department.getName());
        department1.setInstitute(department.getInstitute());
        departmentRepositories.save(department1);
    }

    @Override
    public void deleteDepartment(int departmentSysid) {
        Department department1 = departmentRepositories.findById(departmentSysid).orElseThrow(()->new ResourceNotFoundException("Department not found"));
        departmentRepositories.delete(department1);
    }

}
