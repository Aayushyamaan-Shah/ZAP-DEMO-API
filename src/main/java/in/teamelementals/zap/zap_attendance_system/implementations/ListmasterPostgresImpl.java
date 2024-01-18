package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.ListmasterRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.ListmasterDAO;
import in.teamelementals.zap.zap_attendance_system.model.Listmaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class ListmasterPostgresImpl implements ListmasterDAO {

    @Autowired
    private ListmasterRepositories listmasterRepositories;
    @Override
    public List<String> getListValueByParameter(String parameter){

        List<String> dropdownlist = new ArrayList<>();
        List<Listmaster> listmasterList = listmasterRepositories.findByParameterAndSysStatus(parameter,"A");
        for (Listmaster listmaster:listmasterList){
            dropdownlist.add(listmaster.getValue());
        }
       return dropdownlist;
    }
}
