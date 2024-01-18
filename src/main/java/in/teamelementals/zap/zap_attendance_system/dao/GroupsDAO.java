package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.Groups;

import java.util.List;

public interface GroupsDAO{

    Boolean addGroup(Groups groups);

    List<Groups> getAll();

    Boolean updateGroup(int groupsSysid,Groups groups);

    void deleteGroup(int groupsSysid,int modifiedUserId);
}
