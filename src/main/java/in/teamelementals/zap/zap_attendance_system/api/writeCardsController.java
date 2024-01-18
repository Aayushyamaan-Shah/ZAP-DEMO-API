package in.teamelementals.zap.zap_attendance_system.api;

import in.teamelementals.zap.zap_attendance_system.dao.writeCardsDAO;
import in.teamelementals.zap.zap_attendance_system.model.writeCards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') || hasRole('HR') || hasRole('LIBRARIAN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/writeCards")
public class writeCardsController {

    @Autowired
    private writeCardsDAO writeCardsDAO;

    @PostMapping("/insert")
    public ResponseEntity<Integer> insert(@RequestBody writeCards writeCards){
        writeCards.setName(writeCards.getName().toUpperCase());
        writeCards.setUserId(writeCards.getUserId().toUpperCase());
        int a = writeCardsDAO.insert(writeCards);
        if(a == 0){
            return new ResponseEntity<>(a, HttpStatus.UNAUTHORIZED);
        } 
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
