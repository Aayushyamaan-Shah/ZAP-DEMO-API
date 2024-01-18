package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.in_out_attendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.in_out_attendanceDAO;
import in.teamelementals.zap.zap_attendance_system.model.Student;
import in.teamelementals.zap.zap_attendance_system.model.getAllUser;
import in.teamelementals.zap.zap_attendance_system.model.in_out_attendance;
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
public class in_out_attendancePostgresImpl implements in_out_attendanceDAO{

    @Autowired
    private in_out_attendanceRepositories inOutAttendanceRepositories;

    @Autowired
    private StudentPostgresImpl studentPostgres;

    @Override
    public Integer insertUser(in_out_attendance inOutAttendance){
        inOutAttendance.setSysStatus("A");

        //TODO: Date filter
        boolean i = inOutAttendanceRepositories.existsByUserAndOutTimeAndMacAndTimerFlag(inOutAttendance.getUser(), null,inOutAttendance.getMac(),true);
        if(i){
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            in_out_attendance inOutAttendance1 = inOutAttendanceRepositories.findByUserAndOutTimeAndMacAndTimerFlag(inOutAttendance.getUser(), null,inOutAttendance.getMac(),true);
            inOutAttendance1.setOutTime(ts);
            inOutAttendance1.setTimerFlag(false);

            inOutAttendanceRepositories.save(inOutAttendance1);
            return 0;
        }
        else {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            inOutAttendance.setInTime(ts);
            inOutAttendance.setOutTime(null);
            inOutAttendance.setTimerFlag(true);
//            inOutAttendance.setCreatedDate(java.sql.Date.valueOf(java.time.LocalDate.now()));

            inOutAttendanceRepositories.save(inOutAttendance);
            return 1;
        }
    }

//    @Override
//    public List<in_out_attendance> getUserByTimeStamp(Timestamp t1,Timestamp t2){
//        List<in_out_attendance> attendanceList = inOutAttendanceRepositories.findByCreatedDateBetween(t1,t2);
//        return attendanceList;
//    }

    @Override
    public List<getAllUser> getAllUser(){

        List<in_out_attendance> lists = inOutAttendanceRepositories.findAll();

        List<getAllUser> finalLists = new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            in_out_attendance single1 = lists.get(i);
            Student student = studentPostgres.selectStudentByRollNo(single1.getUser());
            getAllUser single2 = new getAllUser();
            single2.setUserId(single1.getUser());
            single2.setName(student.getUser().getName());
//            single2.setDate(single1.getCreatedDate());
            single2.setProgramType(student.getProgramType());
            single2.setDeegre(student.getDegree());
            single2.setCurrentSemester(student.getCurrentSemester());
            single2.setInstitute(student.getInstitute());
            single2.setDivison(student.getDivision());
            single2.setBatch(student.getBatch());
            single2.setInTime(single1.getInTime());
            single2.setOutTime(single1.getOutTime());
            single2.setLocation("LIBRARY");
            finalLists.add(single2);
        }
        return finalLists;
    }
}
