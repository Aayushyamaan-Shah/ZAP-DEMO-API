package in.teamelementals.zap.zap_attendance_system.implementations;

import java.sql.Timestamp;
import java.util.Date;
import in.teamelementals.zap.zap_attendance_system.Repositories.DeviceRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.writerDevicesRepositories;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.DeviceResponse;
import in.teamelementals.zap.zap_attendance_system.model.writerDevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import in.teamelementals.zap.zap_attendance_system.dao.DeviceDAO;
import in.teamelementals.zap.zap_attendance_system.model.Device;

@Primary
@Repository
@Qualifier("postgresRepo")
public class DevicePostgresImpl implements DeviceDAO{

    @Autowired
    private DeviceRepositories deviceRepositories;

    @Autowired
    private writerDevicesRepositories writerDevicesRepositories;

    @Override
    public DeviceResponse addDevice(Device device,String deviceRole){
        Boolean a = deviceRepositories.existsByNumberAndSysStatusOrMacAndSysStatus(device.getNumber(),"A", device.getMac(), "A");
        if(!a){
            Device d = deviceRepositories.findByMacAndSysStatus(device.getMac(),"D");
            if (d==null) {
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                device.setCreatedDate(ts);
                Device device1 = deviceRepositories.save(device);

                writerDevices writerDevices = new writerDevices();
                writerDevices.setCreatedDate(ts);
                writerDevices.setRole(deviceRole);
                writerDevices.setSysStatus("A");
                writerDevices.setCreatorId(device.getCreatorId());
                writerDevicesRepositories.save(writerDevices);

                DeviceResponse response = new DeviceResponse();
                response.setStatus(true);
                response.setId(device1.getDeviceId());
                response.setCheck("I"); //Inserted
                return response;
            }
            else {
                DeviceResponse response = new DeviceResponse();
                response.setStatus(false);
                response.setId(d.getDeviceId());
                response.setCheck("D"); //Deleted
                return response;
            }
        }
        else{
            DeviceResponse response = new DeviceResponse();
            response.setStatus(false);
            response.setId(null);
            response.setCheck("A"); //Already there
            return response;
        }
    }

    @Override
    public void updateDevice(int deviceId,Device device){
        Device device1 = deviceRepositories.findById(deviceId).orElseThrow(()->new ResourceNotFoundException("Device is not found"));
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        device1.setModifiedDate(ts);
        device1.setSysStatus("A");
        device1.setModifierId(device.getCreatorId());
        device1.setStatus(device.getStatus());
        device1.setName(device.getName());
        device1.setNumber(device.getNumber());
        device1.setFirmwareVersion(device.getFirmwareVersion());
        device1.setMac(device.getMac());
        deviceRepositories.save(device1);
    }

    @Override
    public Integer deleteDevice(int deviceId){
        Device device1 = deviceRepositories.findById(deviceId).orElseThrow(()->new ResourceNotFoundException("Device is not found"));
        if(device1!=null){
          writerDevices writerDevices =  writerDevicesRepositories.findBySysStatus("A");
          if (writerDevices!=null){
              writerDevices.setSysStatus("D");
              writerDevicesRepositories.save(writerDevices);
          }
        }
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        device1.setModifiedDate(ts);
        device1.setSysStatus("D");
        deviceRepositories.save(device1);
        return 1;
    }
}
