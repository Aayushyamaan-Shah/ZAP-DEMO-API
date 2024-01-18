package in.teamelementals.zap.zap_attendance_system.Repositories;

import in.teamelementals.zap.zap_attendance_system.model.Listmaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListmasterRepositories extends JpaRepository<Listmaster,Integer>{

    List<Listmaster> findByParameterAndSysStatus(String parameter,String sysStatus);

}
