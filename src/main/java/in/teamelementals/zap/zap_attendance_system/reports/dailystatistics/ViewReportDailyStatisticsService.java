package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class ViewReportDailyStatisticsService{

    @Autowired
    private ViewReportDailyStatisticsRepository vrdsr;

    public List<String> getAllStatus(){
        return vrdsr.findAllDistinctStatus();
    }

    public Page<ViewReportDailyStatistics> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir){
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return vrdsr.findAll(pageable);
    }

    public Page<ViewReportDailyStatistics> findAllFiltered(
        int studentsCount,
        String faculty,
        Date date,
        String status,
        Pageable pageable
    ){
        return vrdsr.findByStudentsCountGreaterThanAndFacultyLikeAndDateAndStatusLike(
            studentsCount,
            faculty,
            date,
            status,
            pageable
        );
    }

}
