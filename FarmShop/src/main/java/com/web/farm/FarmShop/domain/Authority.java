package com.web.farm.FarmShop.domain;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "roleId"})
})
public class Authority  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "userName")
    private Account account;
    @ManyToOne  @JoinColumn(name = "roleId")
    private Role role;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", role=" + role +
                ", account=" + account +
                '}';
    }
}
