package in.teamelementals.zap.zap_attendance_system.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Resource not found Exception");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
