package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "writer_cards")
public class writeCards {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "writer_cards_sysid")
    int writerCardsSysid;

    @Column(name = "creator_id")
    Integer creatorId;

    @Column(name = "created_date")
    Timestamp createdDate;

    @Column(name = "sys_status")
    String sysStatus;

    String name;

    @Column(name = "user_id")
    String userId;

    @Column(name = "device_mac")
    String deviceMac;

    @Column(name = "is_written")
    int isWritten;
}

