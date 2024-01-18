package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.in0utFacultyAttendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.in0utFacultyAttendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.in0utFacultyAttendance;
import in.teamelementals.zap.zap_attendance_system.model.inOutFacultyCustom;
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
public class in0utFacultyAttendancePostgresImpl implements in0utFacultyAttendanceDAO {

    @Autowired
    private in0utFacultyAttendanceRepositories in0utFacultyAttendanceRepositories;

    @Override
    public Integer insert(inOutFacultyCustom inOutFacultyCustom){
        in0utFacultyAttendance in0utFacultyAttendance = new in0utFacultyAttendance();
        in0utFacultyAttendance.setSysStatus("A");
        in0utFacultyAttendance.setMac(inOutFacultyCustom.getMac());
        in0utFacultyAttendance.setUser(inOutFacultyCustom.getUser());
        //new
        boolean i = in0utFacultyAttendanceRepositories.existsByUserAndOutTimeAndMacAndTimerFlag(inOutFacultyCustom.getUser(),null,inOutFacultyCustom.getMac(),true);
        if((inOutFacultyCustom.getType()==1 && i)||(inOutFacultyCustom.getType()==0 && !i)){
            return 0;
        }

        if(i){
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            in0utFacultyAttendance in0utFacultyAttendance1 = in0utFacultyAttendanceRepositories.findByUserAndOutTimeAndMacAndTimerFlag(inOutFacultyCustom.getUser(), null,inOutFacultyCustom.getMac(),true);
            in0utFacultyAttendance1.setOutTime(ts);
            in0utFacultyAttendance1.setTimerFlag(false);
            System.out.println(ts);

            in0utFacultyAttendanceRepositories.save(in0utFacultyAttendance1);
            return 0;
        }
        else {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            in0utFacultyAttendance.setInTime(ts);
            in0utFacultyAttendance.setOutTime(null);
            in0utFacultyAttendance.setTimerFlag(true);
            in0utFacultyAttendance.setCreatedDate(java.sql.Date.valueOf(java.time.LocalDate.now()));

            in0utFacultyAttendanceRepositories.save(in0utFacultyAttendance);
            return 1;
        }
    }
}
