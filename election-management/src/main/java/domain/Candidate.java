package domain;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public record Candidate(String id,
                        Optional<String> photo,
                        String givenName,
                        String familyName,
                        String email,
                        Optional<String> phone,
                        Optional<String> jobTitle) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$");

    public static Candidate create(Optional<String> photo,
                                   String givenName,
                                   String familyName,
                                   String email,
                                   Optional<String> phone,
                                   Optional<String> jobTitle) {
        validateEmail(email); // Valida o email
        return new Candidate(UUID.randomUUID().toString(), photo, givenName, familyName, email, phone, jobTitle);
    }

    private static void validateEmail(String email) {
        if (email == null || email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid.");
        }
    }
}
