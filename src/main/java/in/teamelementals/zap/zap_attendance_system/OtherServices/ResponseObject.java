package in.teamelementals.zap.zap_attendance_system.OtherServices;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tblActualTimeTable")
public class ResponseObject {

    @XmlElement(name = "TTDate")
    private String Date;

    @XmlElement(name = "LecDetails")
    private String LecDeatils;

    @XmlElement(name = "Subject")
    private String subject;

}
