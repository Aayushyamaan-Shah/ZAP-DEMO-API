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
@Table(name = "buildings")
public class Buildings{

    @Id
    @Column(name = "building_sysid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer buildingSysid;
    @Column(name = "creator_date")
    Timestamp createdDate;
    @Column(name = "creator_id")
    Integer creatorId;
    Integer floor;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_user_id")
    Integer modifiedUserId;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "name")
    String name;

    @Column(name = "number")
    String number;
}
