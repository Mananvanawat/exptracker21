package com.example.exptracker21.model;

import com.example.exptracker21.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoData extends UserInfo {
    private String userName;
    private String lastname;
    private Long phoneNumber;
    private String email;
}
