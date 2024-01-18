package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.GroupedDevices;

import java.util.List;

public interface GroupedDevicesDAO{

    Boolean addDevice(GroupedDevices groupedDevices);
    List<GroupedDevices> getByGroup(int groupId);

    Boolean updateDevice(int groupedDevicesSysid,GroupedDevices groupedDevices);

    void deleteDevice(int groupedDevicesSysid,int modifiedUserId);

    void deleteDeviceByGroup(int groupId,int modifiedUserId);
}
