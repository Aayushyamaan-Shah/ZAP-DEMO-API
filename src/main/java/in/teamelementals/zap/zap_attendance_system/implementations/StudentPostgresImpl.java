package in.teamelementals.zap.zap_attendance_system.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import in.teamelementals.zap.zap_attendance_system.Repositories.RoleRepositories;
import in.teamelementals.zap.zap_attendance_system.Repositories.UserRepositories;
import in.teamelementals.zap.zap_attendance_system.helper.Helper;
import in.teamelementals.zap.zap_attendance_system.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import in.teamelementals.zap.zap_attendance_system.dao.StudentDAO;

@Primary
@Repository
@Qualifier("postgresRepo")
public class StudentPostgresImpl implements StudentDAO{

    private final JdbcTemplate jdbcTemplate;

    public StudentPostgresImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private RoleRepositories roleRepositories;

    @Value("${student.role.id}")
    private String studentRoleId;

    @Autowired
    private UserPostgresImpl userPostgres;

    @Override
    @Transactional
    public Integer insertStudent(Student student) {
        String sql = """
                SELECT * FROM users
                WHERE
                enrollment_no = ?;
                """;

        Role role = roleRepositories.findById(studentRoleId).get();
        student.getUser().getRoles().add(role);

        boolean test = jdbcTemplate.queryForList(sql, student.getUser().getEnrollmentNo()).isEmpty();
        Logger.getLogger("TES Logger").info("TEST: "+student.getUser().getRole().equalsIgnoreCase("ROLE_STUDENT"));
        if(!test || !student.getUser().getRole().equalsIgnoreCase("ROLE_STUDENT")){
            // User already exists
            // OR
            // The role was not correct
            Logger.getLogger("TES Logger").info("The user already exists for id: "+student.getUser().getEnrollmentNo());
            return 0;
        }
//        final String isql = """
//            INSERT INTO users(
//                created_date,
//                creator_id,
//                sys_status,
//                account_status,
//                name,
//                contact,
//                email,
//                role,
//                password_hash,
//                salt,
//                enrollment_no
//            )
//            values
//            (now(), ?, ?, ?, ?, ?, ?, ?, ?, ?,?);
//                """;
//        User user = student.getUser();
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            String internalSql = isql;
//            PreparedStatement ps = connection
//                .prepareStatement(internalSql, Statement.RETURN_GENERATED_KEYS);
//                ps.setInt(1,user.getCreatorId());
//                ps.setString(2,user.getSysStatus());
//                ps.setString(3,user.getAccountStatus());
//                ps.setString(4,user.getName());
//                ps.setLong(5,user.getContact());
//                ps.setString(6,user.getEmail());
//                ps.setString(7, "STUDENT");
//                ps.setString(8,user.getPassword());
//                ps.setString(9,user.getSalt());
//                ps.setString(10,user.getEnrollmentNo());
//
//                return ps;
//            },keyHolder);
//        int registeredUserId = 0;
//        try{
//            registeredUserId = Integer.parseInt(keyHolder.getKeyList().get(0).toString().split(",")[0].split("=")[1]);
//        }catch(NullPointerException e){
//            Logger.getLogger(TESConstants.LOGGER_NAME).warning("User was not inserted.");
//            Logger.getLogger(TESConstants.LOGGER_NAME).warning(e.getMessage());
//        }

           User user =  userPostgres.addUser(student.getUser());

            sql = """
            INSERT INTO student(
                student_sysid,
                created_date,
                creator_id,
                sys_status,
                institute,
                program_type,
                admission_semester,
                degree,
                current_semester,
                division,
                batch,
                admission_type,
                date_of_joining
            )
            values
            (?,now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
            jdbcTemplate.update(
                sql,
                // userRef.getUserId(),
                user.getUserId(),
                student.getCreatorId(),
                student.getSysStatus(),
                student.getInstitute(),
                student.getProgramType(),
                student.getAdmissionSemester(),
                student.getDegree(),
                student.getCurrentSemester(),
                student.getDivision(),
                student.getBatch(),
                student.getAdmissionType(),
                student.getDateOfJoining()
            );
            return 1;
    }

    @Override
    public PageableResponse<Student> selectAllStudent(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        String sql = "";
        sql = "SELECT * FROM student LEFT JOIN users ON student.student_sysid = users.user_sysid;";
        List<Student> studentList = jdbcTemplate.query(sql, (resultSet, i) -> {
            Student student = new Student();
            User user = new User();

            user.setUserId(resultSet.getInt(User.SYSTEM_ID_STRING));
            user.setSysStatus(resultSet.getString(User.SYSTEM_STATUS_STRING));
            user.setCreatedDate(resultSet.getTimestamp(User.CREATED_DATE_STRING));
            user.setCreatorId(resultSet.getInt(User.CREATOR_ID_STRING));
            user.setModifiedDate(resultSet.getTimestamp(User.MODIFIED_DATE_STRING));
            user.setModifierId((resultSet.getInt(User.MODIFIER_USER_ID_STRING)));
            user.setName(resultSet.getString(User.NAME_STRING));
            user.setContact(resultSet.getLong(User.CONTACT_STRING));
            user.setEmail(resultSet.getString(User.EMAIL_STRING));
            user.setRole(resultSet.getString(User.ROLE_STRING));
            user.setPassword(resultSet.getString(User.PASSWORD_STRING));
            user.setAccountStatus(resultSet.getString(User.ACCOUNT_STATUS_STRING));
            user.setSalt(resultSet.getString(User.SALT_STRING));
            user.setEnrollmentNo(resultSet.getString(User.ENROLLMENT_NUMBER_STRING));

            student.setUser(user);
            student.setStudentId(resultSet.getInt(Student.SYSTEM_ID_STRING));
            student.setCreatedDate(resultSet.getTimestamp(Student.CREATED_DATE_STRING));
            student.setCreatorId(resultSet.getInt(Student.CREATOR_ID_STRING));
            student.setModifiedDate(resultSet.getTimestamp(Student.MODIFIED_DATE_STRING));
            student.setModifierId(resultSet.getInt(Student.MODIFIER_USER_ID_STRING));
            student.setAdmissionSemester(resultSet.getInt(Student.ADMISION_SEMESTER_STRING));
            student.setSysStatus(resultSet.getString(Student.SYSTEM_STATUS_STRING));
            student.setInstitute(resultSet.getString(Student.INSTITUTE_STRING));
            student.setProgramType(resultSet.getString(Student.PROGRAM_TYPE_STRING));
            student.setAdmissionSemester(resultSet.getInt(Student.ADMISION_SEMESTER_STRING));
            student.setCurrentSemester(resultSet.getInt(Student.CURRENT_SEMESTER_STRING));
            student.setDivision(resultSet.getString(Student.DIVISION_STRING));
            student.setBatch(resultSet.getString(Student.BATCH_STRING));
            student.setDegree(resultSet.getString(Student.DEGREE_STRING));
            student.setAdmissionType(resultSet.getString(Student.ADMISSION_TYPE_STRING));
            student.setDateOfJoining(resultSet.getDate(Student.DATE_OF_JOINING_STRING));

            return student;
        });

        Page<Student> page = new PageImpl<Student>(studentList,pageable,studentList.size());
        return Helper.getPageableResponse(page,Student.class);
    }

    @Override
    public Student selectStudentByRollNo(String rollNo){

        String sql = "SELECT * FROM student LEFT JOIN users ON student.student_sysid = users.user_sysid WHERE LOWER(users.enrollment_no) = LOWER('"+rollNo.trim()+"');";
        try{

            return jdbcTemplate.query(sql, (resultSet, i) -> {
                Student student = new Student();
                User user = new User();

                user.setUserId(resultSet.getInt(User.SYSTEM_ID_STRING));
                user.setSysStatus(resultSet.getString(User.SYSTEM_STATUS_STRING));
                user.setCreatedDate(resultSet.getTimestamp(User.CREATED_DATE_STRING));
                user.setCreatorId(resultSet.getInt(User.CREATOR_ID_STRING));
                user.setModifiedDate(resultSet.getTimestamp(User.MODIFIED_DATE_STRING));
                user.setModifierId((resultSet.getInt(User.MODIFIER_USER_ID_STRING)));
                user.setName(resultSet.getString(User.NAME_STRING));
                user.setContact(resultSet.getLong(User.CONTACT_STRING));
                user.setEmail(resultSet.getString(User.EMAIL_STRING));
                user.setRole(resultSet.getString(User.ROLE_STRING));
                user.setPassword(resultSet.getString(User.PASSWORD_STRING));
                user.setAccountStatus(resultSet.getString(User.ACCOUNT_STATUS_STRING));
                user.setSalt(resultSet.getString(User.SALT_STRING));

                student.setUser(user);
                student.setStudentId(resultSet.getInt(Student.SYSTEM_ID_STRING));
                student.setCreatedDate(resultSet.getTimestamp(Student.CREATED_DATE_STRING));
                student.setCreatorId(resultSet.getInt(Student.CREATOR_ID_STRING));
                student.setModifiedDate(resultSet.getTimestamp(Student.MODIFIED_DATE_STRING));
                student.setModifierId(resultSet.getInt(Student.MODIFIER_USER_ID_STRING));
                student.setAdmissionSemester(resultSet.getInt(Student.ADMISION_SEMESTER_STRING));
                student.setSysStatus(resultSet.getString(Student.SYSTEM_STATUS_STRING));
                student.setInstitute(resultSet.getString(Student.INSTITUTE_STRING));
                student.setProgramType(resultSet.getString(Student.PROGRAM_TYPE_STRING));
                student.setCurrentSemester(resultSet.getInt(Student.CURRENT_SEMESTER_STRING));
                student.setDivision(resultSet.getString(Student.DIVISION_STRING));
                student.setBatch(resultSet.getString(Student.BATCH_STRING));
                student.setDegree(resultSet.getString(Student.DEGREE_STRING));
                student.setAdmissionType(resultSet.getString(Student.ADMISSION_TYPE_STRING));
                student.setDateOfJoining(resultSet.getDate(Student.DATE_OF_JOINING_STRING));

                return student;
            }).get(0);

        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public List<Student> selectStudentByID(Integer id) {
        return new ArrayList<>();
    }

    @Override
    public List<Student> selectStudentByName(String name, Integer page) {
        return new ArrayList<>();
}

    @Override
    @Transactional
    public Integer deleteStudentByRollNo(String rollNo){
        Student student = selectStudentByRollNo(rollNo);
        if(student == null){
            return 0;
        }

        String sql = """
                UPDATE student SET sys_status = 'D' WHERE student_sysid = ? ;
                """;
        if(jdbcTemplate.update(sql, student.getStudentId()) == 1){
            sql = """
                UPDATE users SET sys_status = 'D' WHERE user_sysid = ? ;
                """;
            return jdbcTemplate.update(sql, student.getUser().getUserId());
        }else{
            return 0;
        }
    }

    @Override
    @Transactional
    public Integer deleteStudentByRollNo(Student student) {
        return 0;
    }

    @Override
    @Transactional
    public Integer updateStudentByRollNo(Student student) {
        return 0;
    }

    @Override
    @Transactional
    public Integer updateStudentByRollNo(String rollNo, Student student) {
        User user = student.getUser();

        String sql = "SELECT user_sysid FROM users WHERE enrollment_no = '"+rollNo+"';";
        int studentSysID = -1;
        try{
            studentSysID = jdbcTemplate.query(sql, (resultSet, i) -> {
                return resultSet.getInt("user_sysid");
            }).get(0);

        }catch(IndexOutOfBoundsException e){
            return 0;
        }


        sql = """
            UPDATE users SET
            modified_date = now(),
            modified_user_id = ?,
            account_status = ?,
            name = ?,
            enrollment_no = ?,
            contact = ?,
            email = ?,
            role = ?,
            password_hash = ?,
            salt = ?
            where user_sysid = ?
            ;
            """;

        if(1 == jdbcTemplate.update(sql,
            user.getModifierId(),
            user.getAccountStatus(),
            user.getName(),
            user.getEnrollmentNo(),
            user.getContact(),
            user.getEmail(),
            user.getRole(),
            user.getPassword(),
            user.getSalt(),
            studentSysID
        )){
            sql = """
                    UPDATE student SET
                    modified_date = now(),
                    modified_user_id = ?,
                    institute = ?,
                    program_type = ?,
                    admission_semester = ?,
                    degree = ?,
                    current_semester = ?,
                    division = ?,
                    batch = ?,
                    admission_type = ?,
                    date_of_joining = ?
                    WHERE
                    student_sysid = ?
                    """;
            return jdbcTemplate.update(sql,
            student.getModifierId(),
            student.getInstitute(),
            student.getProgramType(),
            student.getAdmissionSemester(),
            student.getDegree(),
            student.getCurrentSemester(),
            student.getDivision(),
            student.getBatch(),
            student.getAdmissionType(),
            student.getDateOfJoining(),
            studentSysID
            );
        }
        return 1;
    }

    @Override
    public HashMap<String, String> insertListOfStudents(List<Student> students) {
        
        HashMap<String, String> hashMap = new HashMap<>();
        for(Student student : students){
            hashMap.put(student.getUser().getEnrollmentNo(), (insertStudent(student) == 1)? "TRUE" : "FALSE" );
        }
        return hashMap;
    }

}
