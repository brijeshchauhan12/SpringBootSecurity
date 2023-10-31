package com.springsecurity.springsecurityclient.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {
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
            foreignKey = @ForeignKey(name="FK_USER_PASSWORD_RESET_KEY")
    )
    private User user;

    public PasswordResetToken(User user, String token){
        super();
        this.user=user;
        this.token=token;
        this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
    }

    public PasswordResetToken(String token){
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
