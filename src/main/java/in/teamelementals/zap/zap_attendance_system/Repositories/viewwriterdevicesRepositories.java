package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewwriterdevices;
import in.teamelementals.zap.zap_attendance_system.model.viewwriterdevicesId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface viewwriterdevicesRepositories extends JpaRepository<viewwriterdevices, viewwriterdevicesId> {

    List<viewwriterdevices> findByAccessibleRole(String accessibleRole);

}
