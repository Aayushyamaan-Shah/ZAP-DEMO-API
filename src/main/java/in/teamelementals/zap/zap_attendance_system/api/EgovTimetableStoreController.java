package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.EgovTimetableStoreDAO;
import in.teamelementals.zap.zap_attendance_system.model.EgovTimetableStore;
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
@RequestMapping(path = "/zap/api/egovTimeTable")
public class EgovTimetableStoreController{

    @Autowired
    private EgovTimetableStoreDAO egovTimetableStoreDAO;

    @GetMapping("/getDate")
    public ResponseEntity<List<EgovTimetableStore>> getDate(
            @RequestParam(name = "date",required = false) Date date,
            @RequestParam(name = "facultyId",defaultValue = "%",required = false) String facultyId,
            @RequestParam(name = "time",defaultValue = "%",required = false) String tttime,
            @RequestParam(name = "facultyName",defaultValue = "%",required = false) String facultyName
    ){
        List<EgovTimetableStore> egovTimetableStoreList = egovTimetableStoreDAO.getDataList(date,tttime,facultyId,facultyName);
        return new ResponseEntity<>(egovTimetableStoreList, HttpStatus.OK);
    }

    @GetMapping("/getDateList")
    public ResponseEntity<List<String>> getDate(
            @RequestParam(name = "facultyId",defaultValue = "%%%",required = false) String facultyId
    ){
        List<String> dateList = egovTimetableStoreDAO.getAllDate(facultyId);
        return new ResponseEntity<>(dateList, HttpStatus.OK);
    }

}
