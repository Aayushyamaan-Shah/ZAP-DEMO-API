package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "listmaster")
public class Listmaster {

    @Id
    @Column(name = "listmaster_sysid")
    Integer listmasterSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "creator_id")
    Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    Integer modifiedUserid;

    @Column(name = "sys_status")
    String sysStatus;

    @Column(name = "parameter")
    String parameter;

    @Column(name = "value")
    String value;

    @Column(name = "information")
    String information;

    @Column(name = "description")
    String description;
}
