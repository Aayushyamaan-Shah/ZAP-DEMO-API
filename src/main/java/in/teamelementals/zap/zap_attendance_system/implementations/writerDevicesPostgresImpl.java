package in.teamelementals.zap.zap_attendance_system.implementations;


import in.teamelementals.zap.zap_attendance_system.Repositories.writerDevicesRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.writerDevicesDAO;
import in.teamelementals.zap.zap_attendance_system.model.writerDevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class writerDevicesPostgresImpl implements writerDevicesDAO {

    @Autowired
    private writerDevicesRepositories writerDevicesRepositories;

    @Override
    public Integer insert(writerDevices writerDevices){

        Boolean a = writerDevicesRepositories.existsBySysStatus("A");
        if (a){
            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());
            writerDevices.setCreatedDate(ts);
            writerDevicesRepositories.save(writerDevices);
            return 1;
        }
        return 0;
    }
}
