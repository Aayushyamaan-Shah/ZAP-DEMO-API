package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ViewReportDailyStatisticsRepository extends PagingAndSortingRepository<ViewReportDailyStatistics,ViewReportDailyStatisticsPK>{

    Page<ViewReportDailyStatistics> findAll(Pageable pageable);

    @Query("SELECT DISTINCT(status) status FROM ReportDailyStatistics")
    List<String> findAllDistinctStatus();

    Page<ViewReportDailyStatistics> findByStudentsCountGreaterThanAndFacultyLikeAndDateAndStatusLike(int studentsCount, String faculty, Date date, String status,Pageable pageable);
}
