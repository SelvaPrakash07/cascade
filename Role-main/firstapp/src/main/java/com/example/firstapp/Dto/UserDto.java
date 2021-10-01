package com.example.firstapp.Dto;


import com.example.firstapp.Model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String username;

    private String password;
    private String roleName;
    private List<Role> roles;


}
