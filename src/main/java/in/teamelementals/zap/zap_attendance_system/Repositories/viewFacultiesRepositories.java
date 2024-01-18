package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewFaculties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface viewFacultiesRepositories extends JpaRepository<viewFaculties,Integer> {

    Page<viewFaculties> findByEnrollmentNoLikeAndNameLikeAndInstituteAndDepartmentLike(String enrollmentNo, String name, int institute, String department, Pageable pageable);
    Page<viewFaculties> findByEnrollmentNoLikeAndNameLikeAndDepartmentLike(String enrollmentNo,String name,String department,Pageable pageable);
    viewFaculties findByEnrollmentNo(String enrollmentNo);

}
