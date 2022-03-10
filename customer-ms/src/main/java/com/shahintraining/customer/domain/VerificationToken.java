package com.shahintraining.customer.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true,chain = true)
@Getter
@Setter
public class VerificationToken {

    public static final int EXPIRE_IN_MINUTES = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER,targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Date expirationDate;

    public void calculateExpiryDate(int expireInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE,expireInMinutes);
        expirationDate = cal.getTime();
    }

    public boolean expired(){
        return expirationDate.after(new Date());
    }

}
