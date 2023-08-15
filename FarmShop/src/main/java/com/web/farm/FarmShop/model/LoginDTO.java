package com.web.farm.FarmShop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean rememberMe;
}
