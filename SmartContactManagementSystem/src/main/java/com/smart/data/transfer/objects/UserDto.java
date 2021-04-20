package com.smart.data.transfer.objects;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private int userId;
    @NotBlank(message = "user name cannot be blank")
    private String userName;
    @NotBlank(message = "Email must be valid")
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String userEmail;
    @NotBlank(message = "Password must not be null")
    private String userPassword;
    private String UserRole;
    private Boolean userEnabledStatus;
    private String userImageUrl;
    private String aboutUser;
    private List<ContactDto> contactDto= new ArrayList<>();
}
