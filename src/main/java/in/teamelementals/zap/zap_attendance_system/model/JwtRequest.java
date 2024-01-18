package in.teamelementals.zap.zap_attendance_system.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtRequest {
    private String enrollmentNo;
    private String password;
}
