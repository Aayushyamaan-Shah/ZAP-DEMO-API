package in.teamelementals.zap.zap_attendance_system.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class viewwriterdevicesId implements Serializable {

    private Integer number;

    private String accessibleRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        viewwriterdevicesId that = (viewwriterdevicesId) o;

        if (!Objects.equals(number, that.number)) return false;
        return Objects.equals(accessibleRole, that.accessibleRole);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(number);
        result = 31 * result + Objects.hashCode(accessibleRole);
        return result;
    }

}
