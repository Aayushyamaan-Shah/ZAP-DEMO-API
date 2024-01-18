package in.teamelementals.zap.zap_attendance_system.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "institutes")
public class Institute{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "institute_sysid")
    int instituteSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    int creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    Integer modifiedUserid;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "name")
    String name;

    @Column(name = "university_name")
    String universityName;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "pincode")
    int pincode;

}
