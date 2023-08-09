package com.web.farm.FarmShop.model;

import javax.validation.constraints.NotEmpty;

import com.web.farm.FarmShop.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    private String name;

    @NotEmpty
    @Length(min=5)
    private String email;


    @NotEmpty
    @Length(min=3)
    private String password;

    private String phone;

    private Date registeredDate;

    private Boolean isActive;

    private Boolean rememberMe;
}
