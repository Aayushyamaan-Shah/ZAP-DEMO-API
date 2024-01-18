package in.teamelementals.zap.zap_attendance_system.exception;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){
        super(message);
    }

    public BadApiRequest(){
        super("Bad Request !!");
    }
}
