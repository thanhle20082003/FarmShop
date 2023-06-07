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
public class AccountDTO implements Serializable {

    @NotEmpty
    @Length(min=4)
    private String username;


    @NotEmpty
    @Length(min=3)
    private String password;

    private Boolean isEdit = false;
}
