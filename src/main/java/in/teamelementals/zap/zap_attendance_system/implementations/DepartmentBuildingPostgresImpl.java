package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.DepartmentBuildingRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.BuildingsDAO;
import in.teamelementals.zap.zap_attendance_system.dao.DepartmentBuildingDAO;
import in.teamelementals.zap.zap_attendance_system.model.Buildings;
import in.teamelementals.zap.zap_attendance_system.model.DepartBuildingCustom;
import in.teamelementals.zap.zap_attendance_system.model.DepartmentBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
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
public class DepartmentBuildingPostgresImpl implements DepartmentBuildingDAO {


    @Autowired
    private DepartmentBuildingRepositories departmentBuildingRepositories;

    @Autowired
    private BuildingsDAO buildingsDAO;

    @Override
    public Boolean add(DepartmentBuilding departmentBuilding){
        Boolean a = departmentBuildingRepositories.existsBySysStatusAndDepartment("A",departmentBuilding.getDepartment());
        if (!a){
            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());
            departmentBuilding.setCreatedDate(ts);
            departmentBuildingRepositories.save(departmentBuilding);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<DepartBuildingCustom> getByDepartment(int department){
        List<DepartBuildingCustom> departBuildingCustomList = new ArrayList<>();
        List<DepartmentBuilding> departmentBuildingList = departmentBuildingRepositories.findByDepartment(department);
        for(DepartmentBuilding departmentBuilding:departmentBuildingList){
            DepartBuildingCustom departBuildingCustom = new DepartBuildingCustom();
            departBuildingCustom.setDepartmentBuildingSysid(departmentBuilding.getDepartmentBuildingSysid());
            departBuildingCustom.setDepartment(departmentBuilding.getDepartment());
            departBuildingCustom.setBuilding(departmentBuilding.getBuilding());
            Buildings buildings = buildingsDAO.getById(departmentBuilding.getBuilding());
            departBuildingCustom.setName(buildings.getName());
            departBuildingCustom.setNumber(buildings.getNumber());
            departBuildingCustomList.add(departBuildingCustom);
        }
        return departBuildingCustomList;
    }
}
