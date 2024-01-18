package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class ViewReportDailyStatisticsPK implements Serializable {
    
    private String faculty;

    private Date date;

    public ViewReportDailyStatisticsPK(String facaulty, Date date){
        this.faculty = facaulty;
        this.date = date;
    }

    // REQUIRED
    public ViewReportDailyStatisticsPK(){
        this.faculty = "";
        this.date = Date.valueOf(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewReportDailyStatistics vrds = (ViewReportDailyStatistics) o;
        return faculty.equals(vrds.getFaculty()) &&
                date.equals(vrds.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, date);
    }

}
