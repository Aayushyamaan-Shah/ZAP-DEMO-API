package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.viewdevicelocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface viewdevicelocationsRepositories extends JpaRepository<viewdevicelocations,Integer> {

    List<viewdevicelocations> findByLocation(int locationId);

    List<viewdevicelocations> findByLocationName(String locationname);

    List<viewdevicelocations> findByStatus(String status);

    List<viewdevicelocations> findByMacLikeAndLocation(String mac,int location);
}
