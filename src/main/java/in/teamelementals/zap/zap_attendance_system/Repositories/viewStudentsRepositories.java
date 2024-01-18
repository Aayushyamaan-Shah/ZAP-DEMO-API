package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewStudents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface viewStudentsRepositories extends JpaRepository<viewStudents,Integer> {

    Page<viewStudents> findByEnrollmentNoLikeAndNameLikeAndInstituteLikeAndDegreeLikeAndCurrentSemesterBetween(String enrollmentNo, String name, String institute, String degree, int currentSemester1, int currentSemester2, Pageable pageable);
}
