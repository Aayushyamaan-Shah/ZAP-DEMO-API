package in.teamelementals.zap.zap_attendance_system.dao;

import in.teamelementals.zap.zap_attendance_system.model.EgovTimetableStore;

import java.util.Date;
import java.util.List;

public interface EgovTimetableStoreDAO {

    Integer addData(EgovTimetableStore egovTimetableStore);

    Integer addDataList(List<EgovTimetableStore> egovTimetableStoreList);

    List<EgovTimetableStore> getDataList(Date date,String tttime,String facultyId,String facultyName);

    List<String> getAllDate(String facultyId);
}
