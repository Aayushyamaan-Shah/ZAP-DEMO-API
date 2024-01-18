package in.teamelementals.zap.zap_attendance_system.api;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import in.teamelementals.zap.zap_attendance_system.dao.UserDAO;
import in.teamelementals.zap.zap_attendance_system.model.User;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@Qualifier("postgresRepo")
@RequestMapping(path = "/zap/api/user")
public class UserController{

    @Autowired 
    private UserDAO userDao;

    @PostMapping(path="/add")
    public @ResponseBody Integer addNewUser (
        @RequestBody User user) {
        return userDao.insertUser(user, false);
    }

    @PostMapping(path="/add/internal")
    public @ResponseBody Integer addNewUserInternal (
        @RequestBody User user) {
            return userDao.insertUser(user, true);
    }
    
    @PutMapping(path="/update/{id}")
    public @ResponseBody Integer updateuserById (@PathVariable Integer id, @RequestBody User user) {
      return userDao.updateUserByID(id, user);
    }
  
    @PutMapping(path="/update")
    public @ResponseBody Integer updateuserById (@RequestBody User user) {
        return userDao.updateUserByID(user);
    }
  
    @GetMapping(path={"/all/{page}","/all"})
    public @ResponseBody Iterable<User> getAllusers(@PathVariable Optional<Integer> page) {
      return userDao.selectAllUsers(page.orElse(0));
    }
  
    @GetMapping(path="/id/{id}")
    public @ResponseBody User getuserByID(@PathVariable("id") Integer id){
        return userDao.selectUserByID(id);
    }
  
    @DeleteMapping(path="/delete/{id}/{modifierId}")
    public @ResponseBody Integer deleteByID(@PathVariable("id") Integer id,
                                            @PathVariable("modifierId") int modifierId){
        return userDao.deleteUserByID(id,modifierId);
    }
  
    @GetMapping(path={"/name/{name}","/name/{name}/{page}"})
    public @ResponseBody List<User> getuserByName(@PathVariable String name, @PathVariable Optional<Integer> page){
        return userDao.selectUserByName(name, page.orElse(0));
    }


}