package com.web.farm.FarmShop.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(200) not null")
    private String name;

    @Column( nullable = false)
    private Integer quantity;

    @Column( nullable = false)
    private Double unitPrice;

    @Column( length = 200)
    private String image;

    @Column(columnDefinition = "nvarchar(500) not null")
    private String description;

    @Column( nullable = false)
    private Float discount;

    @Temporal( TemporalType.DATE)
    private Date enteredDate;

    @Column( nullable = false)
    private short status;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", enteredDate=" + enteredDate +
                ", status=" + status +
                '}';
    }

}
