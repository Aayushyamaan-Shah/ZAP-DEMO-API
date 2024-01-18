package in.teamelementals.zap.zap_attendance_system.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacultyUserCustom{

    User user;

    Integer facultySysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    Timestamp createdDate;

    Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    Timestamp modifiedDate;

    Integer modifiedUserid;

    String sysStatus;

    String programType;

    Integer institute;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "IST")
    Date dateOfJoining;

    String department;

    String designation;

    Integer reportingAuthority;

    String instituteName;
}
