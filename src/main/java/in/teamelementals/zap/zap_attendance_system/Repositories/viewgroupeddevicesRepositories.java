package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewgroupeddevices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface viewgroupeddevicesRepositories extends JpaRepository<viewgroupeddevices,Integer>{

    List<viewgroupeddevices> findByGroupId(int group);
}
