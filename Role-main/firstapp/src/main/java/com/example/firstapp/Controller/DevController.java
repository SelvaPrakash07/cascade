package com.example.firstapp.Controller;


import com.example.firstapp.Dto.UserDto;
import com.example.firstapp.Dto.UserSignupRequestVO;
import com.example.firstapp.Model.User;
import com.example.firstapp.Service.Service;
import com.example.firstapp.response.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class DevController {

    @Autowired
    private Service service;


    @PostMapping(value="/save")
    public BaseResponse<User> Saveuser(@RequestBody UserDto userDTO){
        BaseResponse<User> baseResponse=null;
        baseResponse = BaseResponse.<User>builder().data(service.Saveuser(userDTO)).build();
        return baseResponse;
    }
    @GetMapping(value = "/user/login", consumes = {"application/json"})
    @ApiOperation(value = "user login ")
    public BaseResponse<UserSignupRequestVO> logOfUser(@RequestBody UserSignupRequestVO userSignupRequestVO) {
        BaseResponse<UserSignupRequestVO> base = null;
        base = BaseResponse.<UserSignupRequestVO>builder().data(service.logOfUser(userSignupRequestVO)).build();
        return base;
    }

    @RolesAllowed(value="USER")
    @GetMapping(value="/hello")
    public String HelloWorld(){
        final String hello = "hello";
        return hello;
    }
    @RolesAllowed(value="ADMIN")
    @GetMapping(value="/bye")
    public String Admin(){
        final String hello = "Admin";
        return hello;
    }


}
