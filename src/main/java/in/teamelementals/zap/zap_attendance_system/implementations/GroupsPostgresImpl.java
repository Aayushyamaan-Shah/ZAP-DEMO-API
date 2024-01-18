package in.teamelementals.zap.zap_attendance_system.implementations;


import in.teamelementals.zap.zap_attendance_system.Repositories.GroupsRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.GroupedDevicesDAO;
import in.teamelementals.zap.zap_attendance_system.dao.GroupsDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.Groups;
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
public class GroupsPostgresImpl implements GroupsDAO {

    @Autowired
    private GroupsRepositories groupsRepositories;

    @Autowired
    private GroupedDevicesDAO groupedDevicesDAO;

    @Override
    public Boolean addGroup(Groups groups){
        Boolean a = groupsRepositories.existsByNameAndSysStatusOrDisplayNameAndSysStatus(groups.getName(),"A",groups.getDisplayName(),"A");
        if (!a) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            groups.setCreatedDate(ts);
            groupsRepositories.save(groups);
            return true;
        }
        return false;
    }

    @Override
    public List<Groups> getAll(){
        return groupsRepositories.findAllBySysStatus("A");
    }

    @Override
    public Boolean updateGroup(int groupsSysid, Groups groups) {
        Boolean a = groupsRepositories.existsByNameAndSysStatusAndGroupsSysidIsNotOrDisplayNameAndSysStatusAndGroupsSysidIsNot(groups.getName(),"A",groupsSysid,groups.getDisplayName(),"A",groupsSysid);
        if (!a) {
            Groups groups1 = groupsRepositories.findById(groupsSysid).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            groups1.setModifiedDate(ts);
            groups1.setModifiedUserId(groups.getModifiedUserId());
            groups1.setSysStatus(groups.getSysStatus());
            groups1.setName(groups.getName());
            groups1.setDisplayName(groups.getDisplayName());
            groupsRepositories.save(groups1);
            return true;
        }
        return false;
    }

    @Override
    public void deleteGroup(int groupsSysid, int modifiedUserId) {
        Groups groups1 = groupsRepositories.findById(groupsSysid).orElseThrow(()->new ResourceNotFoundException("Group not found"));
        groupedDevicesDAO.deleteDeviceByGroup(groups1.getGroupsSysid(),modifiedUserId);
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        groups1.setModifiedDate(ts);
        groups1.setModifiedUserId(modifiedUserId);
        groups1.setSysStatus("D");
        groupsRepositories.save(groups1);
    }

}
