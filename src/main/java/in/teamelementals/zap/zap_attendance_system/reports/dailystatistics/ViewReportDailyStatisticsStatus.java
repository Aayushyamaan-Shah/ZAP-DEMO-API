package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "view_report_daily_statistics")
public class ViewReportDailyStatisticsStatus {
    @Id
    String status;
}
