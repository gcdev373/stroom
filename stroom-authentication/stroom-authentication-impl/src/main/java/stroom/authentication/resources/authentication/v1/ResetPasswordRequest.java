package stroom.authentication.resources.authentication.v1;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ResetPasswordRequest {

    @NotNull
    @ApiModelProperty(value = "The new password.", required = true)
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
