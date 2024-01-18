package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "viewegovfacultylist")
public class viewEgovFacultyList {

    @Id
    int faculty;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "table_from")
    String tableFrom;
}
