package in.teamelementals.zap.zap_attendance_system.reports.dailystatistics;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') || hasRole('HR')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/reports/daily")


public class ViewReportDailyStatisticsController {

    @Autowired
    private ViewReportDailyStatisticsService viewReportDailyStatisticsService;

    @GetMapping(value="/all")
    public @ResponseBody Page<ViewReportDailyStatistics> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "date",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        return viewReportDailyStatisticsService.getAll(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping(value="/filtered")
    public @ResponseBody Page<ViewReportDailyStatistics> getAllFiltered(
            @RequestParam(value = "studentsCount", defaultValue = "-1", required = false) int studentsCount,
            @RequestParam(value = "faculty", defaultValue = "%", required = false) String faculty,
            @RequestParam(value = "date", defaultValue = "2000-01-01", required = false) Date date,
            @RequestParam(value = "status", defaultValue = "%", required = false) String status,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "20",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "date",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        if(pageNumber <= 0){
            pageNumber = 0;
        }else{
            pageNumber-=1;
        }
        if(date.toString().equals("2000-01-01")){
            date = Date.valueOf(LocalDate.now());
        }
        if(pageSize < 2){
            pageSize = 2;
        }else if(pageSize > 100){
            pageSize = 100;
        }
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        System.out.println("StudentsCount: "+studentsCount);
        System.out.println("Faculty: "+faculty);
        System.out.println("Date: "+date);
        System.out.println("Status: "+status);
        return viewReportDailyStatisticsService.findAllFiltered(studentsCount, faculty, date, status, pageable);
    }


    
    @GetMapping(value="/status")
    public @ResponseBody List<String> getAllStatus(
    ) {
        return viewReportDailyStatisticsService.getAllStatus();
    }


}
