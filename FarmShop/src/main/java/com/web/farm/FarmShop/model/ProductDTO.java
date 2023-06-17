package com.web.farm.FarmShop.model;

import com.web.farm.FarmShop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private Long id;

    private String name;

    private Integer quantity;

    private Double unitPrice;

    private String image;

    private MultipartFile imageFile;

    private String description;

    private Float discount;

    private Date enteredDate;

    private short status;

    private Long categoryId;

    private Boolean isEdit = false;
}
