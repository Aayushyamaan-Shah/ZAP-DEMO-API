package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.InstituteRepositories;
import in.teamelementals.zap.zap_attendance_system.api.UserController;
import in.teamelementals.zap.zap_attendance_system.dao.InstituteDAO;
import in.teamelementals.zap.zap_attendance_system.exception.ResourceNotFoundException;
import in.teamelementals.zap.zap_attendance_system.model.Institute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class InstitutePostgreImpl implements InstituteDAO {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private InstituteRepositories instituteRepositories;

    @Override
    public int addInstitute(Institute institute){
        boolean a = instituteRepositories.searchByNameAndSysStatus(institute.getName(),"A");
        if (a){
            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());
            institute.setCreatedDate(ts);
            logger.info("value "+institute.getModifiedUserid());
            instituteRepositories.save(institute);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Institute> getInstitutes(String sysStatus){
        return instituteRepositories.findBySysStatus(sysStatus);
    }

    @Override
    public void updateInstitute(int instituteSysid, Institute institute){
        Institute institute1 = instituteRepositories.findById(instituteSysid).orElseThrow(()->new ResourceNotFoundException("Institute not found"));
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        institute1.setModifiedDate(ts);
        institute1.setModifiedUserid(institute.getModifiedUserid());
        institute1.setName(institute.getName());
        institute1.setCity(institute.getCity());
        institute1.setAddress(institute.getAddress());
        institute1.setPincode(institute.getPincode());
        institute1.setUniversityName(institute.getUniversityName());
        instituteRepositories.save(institute1);
    }

    @Override
    public void deleteInstitute(int instituteSysid){
        Institute institute = instituteRepositories.findById(instituteSysid).orElseThrow(()->new ResourceNotFoundException("Institute not found"));
        institute.setSysStatus("D");
        instituteRepositories.save(institute);
    }
}
