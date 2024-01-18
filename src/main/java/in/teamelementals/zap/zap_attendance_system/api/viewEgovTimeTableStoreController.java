package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewEgovTimeTableStoreRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewEgovTimeTableStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@CrossOrigin
@Controller
@Qualifier("postgresRepo")
@PreAuthorize("hasRole('ADMIN') || hasRole('FACULTY') || hasRole('HR') || hasRole('LIBRARIAN')")
@RequestMapping(path = "/zap/api/egovTimeTableStore")
public class viewEgovTimeTableStoreController{

    @Autowired
    private viewEgovTimeTableStoreRepositories viewEgovTimeTableStoreRepositories;


    @GetMapping("/getDateList")
    public ResponseEntity<List<String>> getDate(
            @RequestParam(name = "facultyId",defaultValue = "%",required = false) String facultyId
    ){
        List<String> dateList = viewEgovTimeTableStoreRepositories.getDate(facultyId);
        return new ResponseEntity<>(dateList, HttpStatus.OK);
    }

    @GetMapping("/getData")
    public ResponseEntity<List<viewEgovTimeTableStore>> getData(
            @RequestParam(name = "date",required = true) Date date,
            @RequestParam(name = "time",required = true) String timeSlot,
            @RequestParam(name = "lectureDetails",required = true) String lectureDetails
    ){
       List<viewEgovTimeTableStore> viewEgovTimeTableStoreList = viewEgovTimeTableStoreRepositories.findByDateAndTimeSlotAndLectureDetails(date,timeSlot,lectureDetails);
       return new ResponseEntity<>(viewEgovTimeTableStoreList,HttpStatus.OK);
    }

    @GetMapping("/getTimedLectureDetails")
    public ResponseEntity<List<String>> getTimedLectureDetails(
            @RequestParam(name = "facultyId",required = true) String facultyId,
            @RequestParam(name = "date",required = true) Date date
    ){
        List<String> dateList = viewEgovTimeTableStoreRepositories.getTimedLectureDetails(facultyId,date);
        return new ResponseEntity<>(dateList, HttpStatus.OK);
    }

}
