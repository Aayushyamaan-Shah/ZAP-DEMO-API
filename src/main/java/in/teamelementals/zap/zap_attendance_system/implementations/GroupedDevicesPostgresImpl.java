package in.teamelementals.zap.zap_attendance_system.implementations;
import in.teamelementals.zap.zap_attendance_system.Repositories.GroupedDevicesRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.GroupedDevicesDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.GroupedDevices;
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
public class GroupedDevicesPostgresImpl implements GroupedDevicesDAO{

    @Autowired
    private GroupedDevicesRepositories groupedDevicesRepositories;

    @Override
    public Boolean addDevice(GroupedDevices groupedDevices){
        Boolean a = groupedDevicesRepositories.existsByDeviceAndSysStatus(groupedDevices.getDevice(),"A");
        if (!a) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            groupedDevices.setCreatedDate(ts);
            groupedDevicesRepositories.save(groupedDevices);
            return true;
        }
        return false;
    }

    @Override
    public List<GroupedDevices> getByGroup(int groupId) {
        return groupedDevicesRepositories.findByGroupIdAndSysStatus(groupId,"A");
    }

    @Override
    public Boolean updateDevice(int groupedDevicesSysid, GroupedDevices groupedDevices){
        Boolean a = groupedDevicesRepositories.existsByDeviceAndSysStatusAndGroupedDevicesSysid(groupedDevices.getDevice(),"A",groupedDevicesSysid);
        if (!a) {
            GroupedDevices groupedDevices1 = groupedDevicesRepositories.findById(groupedDevicesSysid).orElseThrow(() -> new ResourceNotFoundException("GroupedDevice not found"));
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            groupedDevices1.setModifiedDate(ts);
            groupedDevices1.setDevice(groupedDevices.getDevice());
            groupedDevices1.setGroupId(groupedDevices.getGroupId());
            groupedDevices1.setSysStatus(groupedDevices.getSysStatus());
            groupedDevices1.setModifiedUserId(groupedDevices.getModifiedUserId());
            groupedDevicesRepositories.save(groupedDevices1);
            return true;
        }
        return false;
    }

    @Override
    public void deleteDevice(int groupedDevicesSysid, int modifiedUserId) {
        GroupedDevices groupedDevices1 = groupedDevicesRepositories.findById(groupedDevicesSysid).orElseThrow(()->new ResourceNotFoundException("GroupedDevice not found"));
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        groupedDevices1.setModifiedDate(ts);
        groupedDevices1.setModifiedUserId(modifiedUserId);
        groupedDevices1.setSysStatus("D");
        groupedDevicesRepositories.save(groupedDevices1);
    }

    @Override
    public void deleteDeviceByGroup(int groupedId, int modifiedUserId) {
        List<GroupedDevices> groupedDevicesList = groupedDevicesRepositories.findByGroupId(groupedId);
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        for (int i=0;i<groupedDevicesList.size();i++){
            groupedDevicesList.get(i).setModifiedDate(ts);
            groupedDevicesList.get(i).setModifiedUserId(modifiedUserId);
            groupedDevicesList.get(i).setSysStatus("D");
        }
        groupedDevicesRepositories.saveAll(groupedDevicesList);
    }


}
