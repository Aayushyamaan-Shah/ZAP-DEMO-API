package in.teamelementals.zap.zap_attendance_system.implementations;
import in.teamelementals.zap.zap_attendance_system.Repositories.LocationRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.DeviceLocationDao;
import in.teamelementals.zap.zap_attendance_system.dao.LocationDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.Location;
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
public class LocationPostgresImpl implements LocationDAO{

    @Autowired
    private LocationRepositories locationRepositories;

    @Autowired
    private DeviceLocationDao deviceLocationDao;

    @Override
    public Boolean addLocation(Location location){
        Boolean a = locationRepositories.existsByNumberAndSysStatus( location.getNumber(), "A");
        if (!a) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            location.setCreatedDate(ts);
            locationRepositories.save(location);
            return true;
        }
        return false;
    }

    @Override
    public List<Location> getAll(){
        List<Location> locations = locationRepositories.findAll();
        return locations;
    }

    @Override
    public List<Location> getByFloorAndBuilding(int floor,int building) {
        return locationRepositories.findByFloorAndBuildingAndSysStatus(floor,building,"A");
    }

    @Override
    public Boolean updateLocation(int locationSysid, Location location) {
        Boolean a = locationRepositories.existsByNumberAndSysStatusAndLocationSysidIsNot(location.getNumber(), "A",locationSysid);
        Location location1 = locationRepositories.findById(locationSysid).orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        if (!a) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            location1.setModifiedDate(ts);
            location1.setModifiedUserId(location.getModifiedUserId());
            location1.setSysStatus(location.getSysStatus());
            location1.setName(location.getName());
            location1.setNumber(location.getNumber());
            location1.setType(location.getType());
            location1.setBuilding(location.getBuilding());
            location1.setFloor(location.getFloor());
            locationRepositories.save(location1);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void deleteLocation(int locationSysid,int modifiedUserId){
        Location location1 = locationRepositories.findById(locationSysid).orElseThrow(()->new ResourceNotFoundException("Location not found"));
        deviceLocationDao.deleteDeviceLocationByLocation(location1.getLocationSysid(),modifiedUserId);
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        location1.setModifiedDate(ts);
        location1.setModifiedUserId(modifiedUserId);
        location1.setSysStatus("D");
        locationRepositories.save(location1);
    }

    @Override
    public void deleteLocationByBuilding(int buildingId,int modifiedUserId){
        List<Location> locationList = locationRepositories.findByBuildingAndSysStatus(buildingId,"A");
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        for (int i=0;i<locationList.size();i++){
            deviceLocationDao.deleteDeviceLocationByLocation(locationList.get(i).getLocationSysid(),modifiedUserId);
            locationList.get(i).setModifiedDate(ts);
            locationList.get(i).setModifiedUserId(modifiedUserId);
            locationList.get(i).setSysStatus("D");
        }
        locationRepositories.saveAll(locationList);
    }

}
