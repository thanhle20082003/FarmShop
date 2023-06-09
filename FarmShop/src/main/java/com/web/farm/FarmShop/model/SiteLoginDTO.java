package com.web.farm.FarmShop.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteLoginDTO {
    @NotEmpty
    private String email;


    @NotEmpty
    private String password;

    private Boolean rememberMe  = false;
}
