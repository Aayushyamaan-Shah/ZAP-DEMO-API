package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewEgovFacultyList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface viewEgovFacultyListRepositories extends JpaRepository<viewEgovFacultyList,Integer> {

    List<viewEgovFacultyList> findByTableFrom(String tableFrom);

}
