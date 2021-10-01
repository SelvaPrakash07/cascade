package com.example.firstapp.Service;


import com.example.firstapp.Dto.UserDto;
import com.example.firstapp.Dto.UserSignupRequestVO;
import com.example.firstapp.Model.User;

public interface Service {
    User Saveuser(UserDto userDto);
    public UserSignupRequestVO logOfUser(UserSignupRequestVO userSignupRequestVO);
}
