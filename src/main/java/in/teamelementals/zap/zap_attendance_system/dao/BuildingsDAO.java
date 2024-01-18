package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.Buildings;

import java.util.List;

public interface BuildingsDAO {

    //create
    Boolean addBuilding(Buildings building);

    Buildings getById(int buildingId);

    //getAll
    List<Buildings> getAll(String sysStatus);

    Boolean updateBuilding(int buildingSysid,Buildings buildings);

    void deleteBuilding(int buildingSysid,int modifiedUserId);

    Boolean checkData(String name,String number);
}
