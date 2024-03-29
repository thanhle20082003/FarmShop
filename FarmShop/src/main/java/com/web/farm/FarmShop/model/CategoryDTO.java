package com.web.farm.FarmShop.model;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private Long id;

    @NotEmpty
    @Length(min = 3)
    private String name;

    private Boolean isEdit = false;
}
