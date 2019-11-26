package dev.esz.blog.security.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtSignUpRequest {
//    @NotBlank
//    @Size(min = 4, max = 40)
//    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

//    @NotBlank
//    @Size(max = 40)
//    @Email
//    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
