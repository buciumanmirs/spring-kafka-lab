package dev.mirotech.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private int amount;
    private String currency;
    private String bankAccountNumber;
    private String notes;
    private LocalDate paymentDate;

    public  String calculateSha256() throws Exception {
        try {
            // Combine the fields with '//' as delimiter
            var data = this.amount + "//" + this.currency + "//" + this.bankAccountNumber;

            // Get an instance of SHA-256 MessageDigest
            var digest = MessageDigest.getInstance("SHA-256");

            // Calculate the hash
            var hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hexadecimal string
            var hexString = new StringBuilder();
            for (var b : hashBytes) {
                var hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Wrap and rethrow the exception if SHA-256 is not supported
            throw new Exception("SHA-256 algorithm not found", e);
        } catch (Exception e) {
            // Throw any other exception that might occur
            throw new Exception("Error while calculating SHA-256 hash", e);
        }
    }

}
