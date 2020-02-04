package stroom.auth.resources.authentication.v1;

import stroom.auth.daos.UserDao.LoginResult;

import java.util.Optional;

import static stroom.auth.daos.UserDao.LoginResult.BAD_CREDENTIALS;
import static stroom.auth.daos.UserDao.LoginResult.DISABLED_BAD_CREDENTIALS;
import static stroom.auth.daos.UserDao.LoginResult.LOCKED_BAD_CREDENTIALS;
import static stroom.auth.daos.UserDao.LoginResult.USER_DOES_NOT_EXIST;
import static stroom.auth.resources.authentication.v1.PasswordValidationFailureType.BAD_OLD_PASSWORD;
import static stroom.auth.resources.authentication.v1.PasswordValidationFailureType.COMPLEXITY;
import static stroom.auth.resources.authentication.v1.PasswordValidationFailureType.LENGTH;
import static stroom.auth.resources.authentication.v1.PasswordValidationFailureType.REUSE;

public class PasswordValidator {

    static Optional<PasswordValidationFailureType> validateLength(String newPassword, int minimumLength) {
        boolean isLengthValid = newPassword != null && (newPassword.length() >= minimumLength);
        return isLengthValid ? Optional.empty() : Optional.of(LENGTH);
    }

    static Optional<PasswordValidationFailureType> validateComplexity(String newPassword, String complexityRegex) {
        boolean isPasswordComplexEnough = newPassword.matches(complexityRegex);
        return isPasswordComplexEnough ? Optional.empty() : Optional.of(COMPLEXITY);
    }


    static Optional<PasswordValidationFailureType> validateAuthenticity(LoginResult loginResult) {
        boolean isPasswordValid = loginResult != BAD_CREDENTIALS
                && loginResult != DISABLED_BAD_CREDENTIALS
                && loginResult != LOCKED_BAD_CREDENTIALS
                && loginResult != USER_DOES_NOT_EXIST;
        return isPasswordValid ? Optional.empty() : Optional.of(BAD_OLD_PASSWORD);
    }

    static Optional<PasswordValidationFailureType> validateReuse(String oldPassword, String newPassword) {
        boolean isPasswordReused = oldPassword.equalsIgnoreCase(newPassword);
        return isPasswordReused ? Optional.of(REUSE) : Optional.empty();
    }
}