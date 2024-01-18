package in.teamelementals.zap.zap_attendance_system.model;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

public class Student {
    public static final String SYSTEM_ID_STRING = "student_sysid";
    public static final String SYSTEM_STATUS_STRING = "sys_status";
    public static final String CREATED_DATE_STRING = "created_date";
    public static final String CREATOR_ID_STRING = "creator_id";
    public static final String MODIFIED_DATE_STRING = "modified_date";
    public static final String MODIFIER_USER_ID_STRING = "modified_user_id";
    public static final String ROLL_NO_STRING = "roll_no";
    public static final String INSTITUTE_STRING = "institute";
    public static final String PROGRAM_TYPE_STRING = "program_type";
    public static final String ADMISION_SEMESTER_STRING = "admission_semester";
    public static final String DEGREE_STRING = "degree";
    public static final String CURRENT_SEMESTER_STRING = "current_semester";
    public static final String DIVISION_STRING = "division";
    public static final String BATCH_STRING = "batch";
    public static final String ADMISSION_TYPE_STRING = "admission_type";
    public static final String DATE_OF_JOINING_STRING = "date_of_joining";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer studentId;
//    @Column(name = "created_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    Timestamp createdDate;
//    @Column(name = "sys_status")
    String sysStatus;
//    @Column(name = "creator_id")
    int creatorId;
//    @Column(name = "modified_date")
     @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    Timestamp modifiedDate;
//    @Column(name = "modified_user_id")
    Integer modifierId;

    @Embedded
    User user;

//    @Column(name = "institute")
    String institute;
//    @Column(name = "program_type")
    String programType;
//    @Column(name = "admission_semester")
    Integer admissionSemester;
//    @Column(name = "degree")
    String degree;
//    @Column(name = "current_semester")
    Integer currentSemester;
//    @Column(name = "divison")
    String division;
//    @Column(name = "batch")
    String batch;
//    @Column(name = "admission_type")
    String admissionType;
//    @Column(name = "date_of_joining")
@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",timezone = "IST")
    Date dateOfJoining;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getSysStatus() {
        return this.sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus;
    }

    public int getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifierId() {
        return this.modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    public String getInstitute() {
        return this.institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getProgramType() {
        return this.programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public Integer getAdmissionSemester() {
        return this.admissionSemester;
    }

    public void setAdmissionSemester(Integer admissionSemester) {
        this.admissionSemester = admissionSemester;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getCurrentSemester() {
        return this.currentSemester;
    }

    public void setCurrentSemester(Integer currentSemestaer) {
        this.currentSemester = currentSemestaer;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getAdmissionType() {
        return this.admissionType;
    }

    public void setAdmissionType(String admissionType) {
        this.admissionType = admissionType;
    }

    public Date getDateOfJoining() {
        return this.dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

}