package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.main_attendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.main_attendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.main_attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class main_attendancePostgresImpl implements main_attendanceDAO {

    @Autowired
    private main_attendanceRepositories mainAttendanceRepositories;

    @Autowired
    private StudentPostgresImpl studentPostgres;

    @Override
    public Integer insertUser(main_attendance mainAttendance) {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        mainAttendance.setSysStatus("A");
        mainAttendance.setStudent(mainAttendance.getStudent());
        mainAttendance.setCreatedDate(ts);
        mainAttendanceRepositories.save(mainAttendance);

        return 1;
    }

    @Override
    public List<getAllUser> getAllUser() {
       return new ArrayList<>();
    }

}
