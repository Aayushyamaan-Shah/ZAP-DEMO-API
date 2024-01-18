package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.EgovTimetableStoreRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.EgovTimetableStoreDAO;
import in.teamelementals.zap.zap_attendance_system.model.EgovTimetableStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class EgovTimetableStorePostgresImpl implements EgovTimetableStoreDAO {

    @Autowired
    private EgovTimetableStoreRepositories egovTimetableStoreRepositories;

    @Override
    public Integer addData(EgovTimetableStore egovTimetableStore){

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        egovTimetableStore.setCreatedDate(ts);
        egovTimetableStore.setSysStatus("A");

        try{
            //TODO:- check if present then dont insert
            egovTimetableStoreRepositories.save(egovTimetableStore);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public Integer addDataList(List<EgovTimetableStore> egovTimetableStoreList) {

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for(EgovTimetableStore egovTimetableStore:egovTimetableStoreList){
            egovTimetableStore.setCreatedDate(ts);
            egovTimetableStore.setSysStatus("A");
            egovTimetableStore.setCreatorId(3);
        }
        try{
            egovTimetableStoreRepositories.saveAll(egovTimetableStoreList);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<EgovTimetableStore> getDataList(Date date, String tttime, String facultyId, String facultyName) {

        List<EgovTimetableStore> egovTimetableStoreList = egovTimetableStoreRepositories.findByTtdateAndTttimeLikeAndFacultyIdLikeAndFacultyNameLike(date,"%"+tttime.toUpperCase()+"%","%"+facultyId.toUpperCase()+"%","%"+facultyName.toUpperCase()+"%");

        return egovTimetableStoreList;
    }

    @Override
    public List<String> getAllDate(String facultyId){

        List<String> dateList = egovTimetableStoreRepositories.getDate(facultyId);

        return dateList;
    }
}
