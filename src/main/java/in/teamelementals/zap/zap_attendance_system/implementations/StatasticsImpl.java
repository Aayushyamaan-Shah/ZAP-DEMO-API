package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.in_out_attendanceRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class StatasticsImpl{

      @Autowired
      private in_out_attendanceRepositories inOutAttendanceRepositories;

    @  Autowired
      private JavaMailSender javaMailSender;

     public Integer attendanceCount(){
         LocalDate dateObj = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         String date1 = dateObj.format(formatter);
         String timeStart = date1 + " 00:00:00.000";
         String timeEnd = date1 + " 23:59:59.999";

         int inOutCount = inOutAttendanceRepositories.countByInTimeBetween(Timestamp.valueOf(timeStart), Timestamp.valueOf(timeEnd));

         SimpleMailMessage mailMessage = new SimpleMailMessage();
         mailMessage.setTo("sales@teamelementals.in");
         mailMessage.setSubject("Today Attendance Summary");
         mailMessage.setText("Today In-Out Attendance Count: "+inOutCount);
         mailMessage.setFrom("noreply@teamelementals.in");
         javaMailSender.send(mailMessage);

         return inOutCount;
     }

}
