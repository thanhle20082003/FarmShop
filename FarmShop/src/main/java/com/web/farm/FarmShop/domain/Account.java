package com.web.farm.FarmShop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    private String username;

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

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<CartItem> cartsItems;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", registeredDate=" + registeredDate +
                ", isActive=" + isActive +
                ", orders=" + orders +
                ", cartsItems=" + cartsItems +
                '}';
    }


}
