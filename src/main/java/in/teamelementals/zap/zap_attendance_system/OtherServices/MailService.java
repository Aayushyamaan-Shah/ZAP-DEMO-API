package in.teamelementals.zap.zap_attendance_system.OtherServices;

import in.teamelementals.zap.zap_attendance_system.Repositories.in0utFacultyAttendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.in_out_attendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.viewmainattendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.model.AttendanceMailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class MailService{

    public static final String NOTIFICATION_LEVEL_ERROR = "ERROR";
    public static final String NOTIFICATION_LEVEL_WARNING = "WARNING";

    @Autowired
    private viewmainattendanceRepositories viewmainattendanceRepositories;

    @Autowired
    private in_out_attendanceRepositories inOutAttendanceRepositories;

    @Autowired
    private in0utFacultyAttendanceRepositories in0utFacultyAttendanceRepositories;

    @Autowired
    private JavaMailSender javaMailSender;

    public void statasticMailService(int uploadedAttendanceCount){
        LocalDate currentDate = LocalDate.now();
        String date1 = String.valueOf(currentDate);

        String timeStart = date1 + " 00:00:00.000";
        String timeEnd = date1 + " 23:59:59.999";

        int inOutStudentCount = inOutAttendanceRepositories.countByInTimeBetween(Timestamp.valueOf(timeStart), Timestamp.valueOf(timeEnd));
        System.out.println(inOutStudentCount);

        int inOutFacultyCount = in0utFacultyAttendanceRepositories.countByCreatedDateBetween(Timestamp.valueOf(timeStart), Timestamp.valueOf(timeEnd));
        System.out.println(inOutFacultyCount);

        int mainStudentCount = viewmainattendanceRepositories.countByCreatedDateBetween(Timestamp.valueOf(timeStart), Timestamp.valueOf(timeEnd));
        System.out.println(mainStudentCount);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("a5.theelementalsquad@gmail.com","logistics@teamelementals.in");
        mailMessage.setSubject("Today Attendance Summary");
        mailMessage.setText("ZAP Attendance Summary Report | Date: "+date1+"\n" +
                "\nStudent Main Attendance Count: "+mainStudentCount+
                "\nStudent In Out Attendance Count: "+inOutStudentCount+
                "\nFaculty In Out Attendance Count: "+inOutFacultyCount+
                "\n\nE-Governance Data Uploaded Count: "+uploadedAttendanceCount
        );
        mailMessage.setFrom("noreply@teamelementals.in");
        javaMailSender.send(mailMessage);
    }

    public void attendanceMailService(AttendanceMailObject attendanceMailObject){

        StringBuilder todayAttendance = new StringBuilder();
        for(int i=0;i<attendanceMailObject.getDate().size();i++){
                todayAttendance.append("\n"+(i+1)+") Date:- "+attendanceMailObject.getDate().get(i)+"\n    Lec Details:- "+attendanceMailObject.getLacDetails().get(i)+"\n    Subject:- "+attendanceMailObject.getSubject().get(i)+"\n    Time:- "+attendanceMailObject.getTime().get(i)+"\n    Total Student:- "+attendanceMailObject.getTotalStudent().get(i)+"\n    Status:- "+attendanceMailObject.getStatus().get(i)+"\n");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        System.out.println("FACULTY EMAIL: "+attendanceMailObject.getEmail());
        mailMessage.setTo("a5.theelementalsquad@gmail.com", attendanceMailObject.getEmail());
        mailMessage.setSubject("ZAP Attendance Summary Report");
        mailMessage.setText("Hello " + attendanceMailObject.getName() + "\n" + "Mail: "+ attendanceMailObject.getEmail() +
                "\n"+
                todayAttendance
        );
        mailMessage.setFrom("noreply@teamelementals.in");
        javaMailSender.send(mailMessage);
    }

    public void notifyMailService(List<String> adminEmails, String level, String notification){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(adminEmails.toArray(new String[0]));
        mailMessage.setSubject("ZAP Attendance - "+level);
        mailMessage.setText(notification);
        mailMessage.setFrom("noreply@teamelementals.in");
        javaMailSender.send(mailMessage);
    }
}
