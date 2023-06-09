package com.web.farm.FarmShop.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO implements Serializable {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private Boolean isAdmin;

    private Boolean rememberMe = false;
}
