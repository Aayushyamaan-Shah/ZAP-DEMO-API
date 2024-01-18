package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.DeviceLocationRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.DeviceLocationDao;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.DeviceLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class DeviceLocationPostgresImpl implements DeviceLocationDao {

    @Autowired
    private DeviceLocationRepositories deviceLocationRepositories;

    @Override
    public Boolean addDeviceLocation(DeviceLocation deviceLocation){
        Boolean a = deviceLocationRepositories.existsByDeviceAndSysStatus(deviceLocation.getDevice(),"A");
        if (!a) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            deviceLocation.setCreatedDate(ts);
            deviceLocation.setModifiedUserId(null);
            deviceLocationRepositories.save(deviceLocation);
            return true;
        }
        return false;
    }

    @Override
    public HashMap<Integer, Boolean> addDeviceLocationList(List<DeviceLocation> deviceLocationList){
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        for(DeviceLocation deviceLocation:deviceLocationList){
            Boolean a = deviceLocationRepositories.existsByDeviceAndSysStatus(deviceLocation.getDevice(),"A");
            if (!a) {
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                deviceLocation.setCreatedDate(ts);
                deviceLocation.setModifiedUserId(null);
                deviceLocationRepositories.save(deviceLocation);
                hashMap.put(deviceLocation.getDevice(),true);
            }
            hashMap.put(deviceLocation.getDevice(),false);
        }

        return hashMap;
    }

    @Override
    public List<DeviceLocation> getDeviceLocationByLocation(int locationId){
        return deviceLocationRepositories.findByLocationAndSysStatus(locationId,"A");
    }

    @Override
    public Boolean updateDeviceLocation(int deviceLocationSysid, DeviceLocation deviceLocation) {
        Boolean a = deviceLocationRepositories.existsByDeviceAndSysStatusAndDeviceLocationSysidIsNot(deviceLocation.getDevice(),"A",deviceLocationSysid);
        if (!a) {
            DeviceLocation deviceLocation1 = deviceLocationRepositories.findById(deviceLocationSysid).orElseThrow(() -> new ResourceNotFoundException("Device not found !!"));
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            deviceLocation1.setModifiedDate(ts);
            deviceLocation1.setModifiedUserId(deviceLocation.getModifiedUserId());
            deviceLocation1.setSysStatus(deviceLocation.getSysStatus());
            deviceLocation1.setDevice(deviceLocation.getDevice());
            deviceLocation1.setLocation(deviceLocation.getLocation());
            deviceLocationRepositories.save(deviceLocation1);
            return true;
        }
        return false;
    }

    @Override
    public void deleteDeviceLocation(int deviceLocationSysid,int modifiedUserId) {
        DeviceLocation deviceLocation1 = deviceLocationRepositories.findById(deviceLocationSysid).orElseThrow(()->new ResourceNotFoundException("Device not found !!"));
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        deviceLocation1.setModifiedDate(ts);
        deviceLocation1.setSysStatus("D");
        deviceLocation1.setModifiedUserId(modifiedUserId);
        deviceLocationRepositories.save(deviceLocation1);
    }

    @Override
    public void deleteDeviceLocationByLocation(int locationId,int modifiedUserId){
        List<DeviceLocation> deviceLocationList = deviceLocationRepositories.findByLocationAndSysStatus(locationId,"A");
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        for(int i=0;i<deviceLocationList.size();i++){
            deviceLocationList.get(i).setModifiedDate(ts);
            deviceLocationList.get(i).setSysStatus("D");
            deviceLocationList.get(i).setModifiedUserId(modifiedUserId);
        }
        deviceLocationRepositories.saveAll(deviceLocationList);
    }

}
