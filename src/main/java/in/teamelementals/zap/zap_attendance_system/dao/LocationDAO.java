package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.Location;

import java.util.List;

public interface LocationDAO{

    Boolean addLocation(Location location);

    List<Location> getAll();

    List<Location> getByFloorAndBuilding(int floor, int building);

    Boolean updateLocation(int locationSysid,Location location);

    void deleteLocation(int locationSysid,int modifiedUserId);

    void deleteLocationByBuilding(int buildingId,int modifiedUserId);
}
