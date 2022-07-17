package com.shahintraining.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public class Customer {
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "customer_id_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private boolean verified = false;
    private String email;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Date creationDate;
    @LastModifiedDate
    private Date modifiedDate;
    @LastModifiedBy
    private String modifiedBy;
    private String phoneNumber;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();


    public void addRole(Role role){
        if (CollectionUtils.isEmpty(roles))
            roles = new HashSet<>();
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        if (!Objects.equals(firstName, customer.firstName)) return false;
        if (!Objects.equals(lastName, customer.lastName)) return false;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
