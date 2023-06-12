package com.web.farm.FarmShop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(100) not null")
    private String name;

    @Column(columnDefinition = "nvarchar(100) not null")
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date registeredDate;

    @Column(nullable = true)
    private Boolean isActive;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

}
