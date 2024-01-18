package in.teamelementals.zap.zap_attendance_system.dao;
import in.teamelementals.zap.zap_attendance_system.model.DeviceLocation;

import java.util.HashMap;
import java.util.List;

public interface DeviceLocationDao{
    Boolean addDeviceLocation(DeviceLocation deviceLocation);

    HashMap<Integer,Boolean> addDeviceLocationList(List<DeviceLocation> deviceLocationList);

    List<DeviceLocation> getDeviceLocationByLocation(int locationId);

    Boolean updateDeviceLocation(int deviceLocationSysid,DeviceLocation deviceLocation);

    void deleteDeviceLocation(int deviceLocationSysid,int modifiedUserId);

    void deleteDeviceLocationByLocation(int locationId,int modifiedUserId);
}
