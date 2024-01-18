package in.teamelementals.zap.zap_attendance_system.dao;
import in.teamelementals.zap.zap_attendance_system.model.Device;
import in.teamelementals.zap.zap_attendance_system.model.DeviceResponse;

public interface DeviceDAO{

    DeviceResponse addDevice(Device device,String deviceRole);

    void updateDevice(int deviceId,Device device);

    Integer deleteDevice(int deviceId);
}
