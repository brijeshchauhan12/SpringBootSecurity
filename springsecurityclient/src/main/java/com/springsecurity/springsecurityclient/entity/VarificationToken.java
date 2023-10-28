package com.springsecurity.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class VarificationToken {
    private static final int EXPIRATION_TIME=10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name="FK_USER_VARIFY_KEY")
    )
    private User user;

    public VarificationToken(User user, String token){
        super();
        this.user=user;
        this.token=token;
        this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
    }

    public VarificationToken(String token){
        super();
        this.token=token;
        this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
    }
    private Date calculateExpirationDate(int expirationtime){
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationtime);
        return new Date(calendar.getTime().getTime());
    }

}
