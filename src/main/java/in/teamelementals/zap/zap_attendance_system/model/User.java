package in.teamelementals.zap.zap_attendance_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Entity
@Table(name = "users")
public class User implements UserDetails {
    public static final String SYSTEM_ID_STRING = "user_sysid";
    public static final String SYSTEM_STATUS_STRING = "sys_status";
    public static final String CREATED_DATE_STRING = "created_date";
    public static final String CREATOR_ID_STRING = "creator_id";
    public static final String MODIFIED_DATE_STRING = "modified_date";
    public static final String MODIFIER_USER_ID_STRING = "modified_user_id";
    public static final String NAME_STRING = "name";
    public static final String CONTACT_STRING = "contact";
    public static final String EMAIL_STRING = "email";
    public static final String ROLE_STRING = "role";
    public static final String PASSWORD_STRING = "password_hash";
    public static final String SALT_STRING = "salt";
    public static final String ACCOUNT_STATUS_STRING = "account_status";
    public static final String ENROLLMENT_NUMBER_STRING = "enrollment_no";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_sysid")
    Integer userId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "created_date")
    Timestamp createdDate;
    @Column(name = "sys_status")
    String sysStatus;
    @Column(name = "creator_id")
    int creatorId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss Z",timezone = "IST")
    @Column(name = "modified_date")
    Timestamp modifiedDate;
    @Column(name = "modified_user_id")
    Integer modifierId;
    String name;
    Long contact;
    String email;
    String role;
    @Column(name = "password_hash")
    String password;
    String salt;
    @Column(name = "account_status")
    String accountStatus;
    @Column(name = "enrollment_no")
    String enrollmentNo;

   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
//        Set<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(getRole())).collect(Collectors.toSet());
        return authorities;
    }
    @Override
    public String getUsername() {
        return this.enrollmentNo;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}