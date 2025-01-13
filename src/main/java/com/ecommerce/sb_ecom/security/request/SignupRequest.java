package com.ecommerce.sb_ecom.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Getter
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Getter
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Getter
    private String password;

    @Setter
    @Getter
    private Set<String> role;


}
