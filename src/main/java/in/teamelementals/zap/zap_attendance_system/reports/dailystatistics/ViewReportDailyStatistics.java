package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "view_report_daily_statistics")
@IdClass(ViewReportDailyStatisticsPK.class)
public class ViewReportDailyStatistics {

    @Column(name = "students_count")
    private Integer studentsCount;

    @Id
    @Column(name="faculty")
    private String faculty;

    
    @Id
    @Column(name = "date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "IST")
    private Date date;
    
    @Column(name="status")
    private String status;
}