package in.teamelementals.zap.zap_attendance_system.OtherServices;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;

    public String generateRandomOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generates a random digit between 0 and 9
        }

        return otp.toString();
    }
}
