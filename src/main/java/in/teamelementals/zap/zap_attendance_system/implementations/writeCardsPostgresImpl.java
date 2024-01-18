package in.teamelementals.zap.zap_attendance_system.implementations;

import in.teamelementals.zap.zap_attendance_system.Repositories.writeCardsRepositories;
import in.teamelementals.zap.zap_attendance_system.dao.writeCardsDAO;
import in.teamelementals.zap.zap_attendance_system.model.cardDataUpload;
import in.teamelementals.zap.zap_attendance_system.model.writeCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Primary
@Repository
@Qualifier("postgresRepo")
public class writeCardsPostgresImpl implements writeCardsDAO {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private writeCardsRepositories writeCardsRepositories;

    @Override
    public Integer insert(writeCards writeCards){

        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        writeCards.setCreatedDate(ts);
        writeCards.setSysStatus("A");
        writeCards.setIsWritten(0);
        writeCardsRepositories.save(writeCards);

        try {
            String apiUrl = "http://172.16.11.95:5348/write-card";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/json"));
            headers.setBearerAuth("zjq/l-t!s5r3d5F?f8yhdXbx9BYPW=DwI6bul8fPa/0SJK4!QY-bdyT1tzEZZE4e");

            cardDataUpload cardDataUpload = new cardDataUpload();
            cardDataUpload.setUser_id(writeCards.getUserId());
            cardDataUpload.setMac(writeCards.getDeviceMac());
            cardDataUpload.setUser_name(writeCards.getName());

            HttpEntity<cardDataUpload> entity = new HttpEntity<>(cardDataUpload, headers);

            restTemplate.exchange(apiUrl, HttpMethod.POST, entity, cardDataUpload.class);
        }
        catch (Exception e){
            return 0;
        }

        return 1;
    }
}
