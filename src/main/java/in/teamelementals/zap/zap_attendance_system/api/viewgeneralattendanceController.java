package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.Repositories.viewgeneralattendanceRepositories;
import in.teamelementals.zap.zap_attendance_system.model.viewgeneralattendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/viewgeneral")
public class viewgeneralattendanceController{

    @Autowired
    private viewgeneralattendanceRepositories viewgeneralattendanceRepositories1;
    @GetMapping("/getStudent")
    public List<viewgeneralattendance> getAllUser(
            @RequestParam(name = "userId",defaultValue = "%",required = false) String userId,
            @RequestParam(name = "name",defaultValue = "%",required = false) String name,
            @RequestParam(name = "institute",defaultValue = "%",required = false) String institute,
            @RequestParam(name = "location",defaultValue = "%",required = false) String location,
            @RequestParam(name = "buildingName",defaultValue = "%",required = false) String buildingName,
            @RequestParam(name = "buildingNumber",defaultValue = "%",required = false) String buildingNumber,
            @RequestParam(name = "dateFrom",required = false) Timestamp dateFrom,
            @RequestParam(name = "dateTo",defaultValue = "2000-05-05 22:46:58.777",required = false) Timestamp dateTo,
            @RequestParam(name = "timeIn",defaultValue = "2000-05-05 22:46:58.777",required = false) Timestamp timeIn,
            @RequestParam(name = "timeOut",required = false) Timestamp timeOut
            ){

        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date1 = dateObj.format(formatter);

        // java.util.Date date = new java.util.Date();
        // Timestamp ts = new Timestamp(date.getTime());
        String timeStart = date1 + " 00:00:00.000";

        if(location.length() < 1){
            location = "%";
        }

        Logger.getLogger("TES Logger").info(timeStart);
        if (dateFrom==null){
            dateFrom = Timestamp.valueOf(timeStart.toString().substring(0,10)+ " 00:00:00.000");
            dateTo = Timestamp.valueOf(date1 + " 23:59:59.999");
        }

        String timeEnd = date1 + " 23:59:59.999";

        if(dateTo.toString().equals("2000-05-05 22:46:58.777")){
            dateTo = Timestamp.valueOf(dateFrom.toString().substring(0,10) + " 23:59:59.999");
        }else{
            dateTo = Timestamp.valueOf(dateTo.toString().substring(0,10) + " 23:59:59.999");
        }

        if(!timeEnd.toString().equals("2000-05-05 22:46:58.777")){
            timeIn = Timestamp.valueOf(dateFrom.toString().substring(0,10) + timeIn.toString().substring(10));            
        }

        if(timeOut==null){
            timeOut = dateTo;
            timeIn = dateFrom;
        }else{
            timeOut = Timestamp.valueOf(dateTo.toString().substring(0,10) + timeOut.toString().substring(10));
        }

        // buildingName = location;
        // buildingNumber = location;

        Logger.getLogger("TES Logger").info("Building Name:" + buildingName.toUpperCase());
        Logger.getLogger("TES Logger").info("Building Number:" + buildingNumber.toUpperCase());
        Logger.getLogger("TES Logger").info("Location:" + location.toUpperCase());
        Logger.getLogger("TES Logger").info("Date From:" + dateFrom);
        Logger.getLogger("TES Logger").info("Date To:" + dateTo);
        Logger.getLogger("TES Logger").info("Time in:" + timeIn);
        Logger.getLogger("TES Logger").info("Time out:" + timeOut);

        List<viewgeneralattendance> list = viewgeneralattendanceRepositories1.findByEnrollmentLikeAndNameLikeAndInstituteLikeAndLocationLikeAndBuildingNameLikeAndAndBuildingNumberLikeAndInTimeBetweenAndInTimeBetween("%"+userId.toUpperCase()+"%","%"+name.toUpperCase()+"%","%"+institute.toUpperCase()+"%","%"+location.toUpperCase()+"%","%"+buildingName.toUpperCase()+"%","%"+buildingNumber.toUpperCase()+"%",dateFrom,dateTo,timeIn,timeOut);

        return list;
    }
}
