package com.web.farm.FarmShop.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id;

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
}
