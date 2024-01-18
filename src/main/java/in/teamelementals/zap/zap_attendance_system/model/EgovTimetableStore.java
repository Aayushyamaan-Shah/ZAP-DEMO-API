package in.teamelementals.zap.zap_attendance_system.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "egov_timetable_store")
public class EgovTimetableStore {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "egov_timetable_store_sysid")
    private Integer egovTimetableStoreSysid;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "creator_id")
    private Integer creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "modified_user_id")
    private Integer modifiedUserid;

    @Column(name = "sys_status")
    private String sysStatus;

    @Column(name = "actual_timetable_id")
    private String actualTimetableId;

    private Date ttdate;

    private String tttime;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "parent_subject_id")
    private String parentSubjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "faculty_name")
    private String facultyName;

    @ElementCollection
    @Column(name = "student_id_all")
    private List<String> studentIdAll;

    @Column(name = "lecture_details")
    private String lectureDetails;

}

