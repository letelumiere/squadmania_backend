package com.likeurator.squadmania_auth.domain.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.*;
import org.hibernate.tuple.GenerationTiming;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userinfo_date")
public class UserinfoDate {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    
    @Column(name = "withdraw")
    private Boolean withdraw;

    @Column(name = "withdraw_done")
    private Boolean withdrawDone;

    @Column(name = "login_rest")
    private Boolean loginRest;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column(name = "withdraw_date")
    private Date withdrawDate;

    @Column(name = "nickname_date")
    private Date nicknameDate;

    @Column(name = "login_date")
    @CurrentTimestamp(timing = GenerationTiming.ALWAYS)
    private Date loginDate;

    @Column(name = "login_rest_date")
    private Date loginRestDate;

    @JoinColumn(name = "user_id")
    private Userinfo userinfo;

}
