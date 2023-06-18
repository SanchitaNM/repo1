package bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/otp/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String mobileNumber) {
        boolean result = otpService.generateOtp(mobileNumber);
        if (result) {
            return ResponseEntity.ok("OTP generated and sent successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate OTP!");
        }
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(mobileNumber, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verification successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP!");
        }
    }
}
