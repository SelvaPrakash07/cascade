package com.example.firstapp.Dto;

import com.example.firstapp.Model.Role;
import com.example.firstapp.Model.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class UserSignupRequestVO {
    private long id;

    private String userName;

    private String jwtToken;

    private String password;

  /*  private List<UserRole> roleList;
*/
    @Override
    public String toString() {
        return "UserSignupRequestVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
