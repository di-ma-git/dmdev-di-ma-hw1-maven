package com.dima.dto;

import com.dima.enums.Role;
import com.dima.validation.CreateAction;
import com.dima.validation.PhoneNumber;
import com.dima.validation.UserRegistrationInfo;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@FieldNameConstants
@UserRegistrationInfo(groups = CreateAction.class)
@Builder
@AllArgsConstructor
public class UserCreateDto {
    @NotEmpty
    @Size(min = 3, max = 255)
    String name;

    @Email(groups = CreateAction.class)
    String email;

    @PhoneNumber(groups = CreateAction.class)
    String phoneNumber;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    String deliveryAddress;

    Role role;

    MultipartFile image;

}
