package bootstrap;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class OtpService {

    public boolean generateOtp(String mobileNumber) {
        // Generate a random OTP (e.g., using a library like Apache Commons)
        String otp = generateRandomOtp();

        // Send the OTP via SMS using Gupshup's API
        boolean result = sendOtpSms(mobileNumber, otp);

        // Return true if OTP was sent successfully, false otherwise
        return result;
    }

    public boolean verifyOtp(String mobileNumber, String otp) {
        // Implement the logic to verify the OTP
        // Return true if the OTP is valid, false otherwise
        // You can store and compare the OTP in memory or using a database
        // Here, we assume the OTP is valid if it matches a hardcoded value
        return otp.equals("123456");
    }

    private String generateRandomOtp() {
        // Implement your logic to generate a random OTP
        // Example: Generate a 6-digit OTP
        Random random = new Random();
        int otpNumber = 100_000 + random.nextInt(900_000);
        return String.valueOf(otpNumber);
    }

    private boolean sendOtpSms(String mobileNumber, String otp) {
        // Gupshup API endpoint
        String apiUrl = "https://api.gupshup.io/sm/api/v1/send";
        // Your Gupshup API key
        String apiKey = "99lgpxxdc3x5fep6wjwgwru7bwamjojtz";

        // Build the request payload
        String message = "Your OTP is: " + otp;
        String url = apiUrl + "?apikey=" + apiKey + "&to=" + mobileNumber + "&text=" + message;

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // Check the response status code
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // SMS sent successfully
            return true;
        } else {
            // Failed to send SMS
            return false;
        }
    }
}

