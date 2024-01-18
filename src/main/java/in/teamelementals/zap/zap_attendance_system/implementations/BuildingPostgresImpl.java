package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.BuildingsRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.BuildingsDAO;
import in.teamelementals.zap.zap_attendance_system.dao.LocationDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.Buildings;
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
public class BuildingPostgresImpl implements BuildingsDAO{

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private BuildingsRepositories buildingsRepositories;

    @Override
    public Boolean addBuilding(Buildings building){

        Boolean a = buildingsRepositories.existsBySysStatusAndNameOrNumberAndSysStatus("A",building.getName(), building.getNumber(), "A");
        if (!a){
            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());
            building.setCreatedDate(ts);
            buildingsRepositories.save(building);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Buildings getById(int buildingId) {
        Buildings buildings = buildingsRepositories.findByBuildingSysidAndSysStatus(buildingId,"A");
        return buildings;
    }

    @Override
    public List<Buildings> getAll(String sysStatus){
        return buildingsRepositories.findBySysStatus(sysStatus);
    }

    @Override
    public Boolean updateBuilding(int buildingSysid, Buildings buildings){
        Boolean a = buildingsRepositories.existsByBuildingSysidIsNotAndSysStatusAndNameOrNumberAndSysStatusAndBuildingSysidIsNot(buildingSysid,"A",buildings.getName(), buildings.getNumber(), "A",buildingSysid);
        Buildings buildings1 = buildingsRepositories.findById(buildingSysid).orElseThrow(() -> new ResourceNotFoundException("Building Not Found"));
        if(!a){
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            buildings1.setModifiedDate(ts);
            buildings1.setModifiedUserId(buildings.getModifiedUserId());
            buildings1.setSysStatus(buildings.getSysStatus());
            buildings1.setName(buildings.getName());
            buildings1.setNumber(buildings.getNumber());
            buildings1.setFloor(buildings.getFloor());
            buildingsRepositories.save(buildings1);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void deleteBuilding(int buildingSysid,int modifiedUserId){
         Buildings buildings = buildingsRepositories.findById(buildingSysid).orElseThrow(()->new ResourceNotFoundException("Building Not Found"));
         locationDAO.deleteLocationByBuilding(buildings.getBuildingSysid(),modifiedUserId);
         Date date = new Date();
         Timestamp ts=new Timestamp(date.getTime());
         buildings.setModifiedDate(ts);
         buildings.setSysStatus("D");
         buildings.setModifiedUserId(modifiedUserId);
         buildingsRepositories.save(buildings);
    }

    @Override
    public Boolean checkData(String name, String number){
        Boolean a = buildingsRepositories.existsBySysStatusAndNameOrNumberAndSysStatus("A",name, number,"A");
        return a;
    }
}
