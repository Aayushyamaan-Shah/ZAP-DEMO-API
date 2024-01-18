package in.teamelementals.zap.zap_attendance_system.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceMailObject{

    int facultyId;

    String name;

    String email;

    List<String> lacDetails;

    List<String> date;

    List<String> time;

    List<String> totalStudent;

    List<String> subject;

    List<String> status;
}
