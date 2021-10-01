package com.example.firstapp.Service;

import com.example.firstapp.Dto.UserDto;
import com.example.firstapp.Dto.UserSignupRequestVO;
import com.example.firstapp.Model.Role;
import com.example.firstapp.Model.User;
import com.example.firstapp.Repository.RoleRepository;
import com.example.firstapp.Repository.UserRepository;
import com.example.firstapp.util.Constants;
import com.example.firstapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceImpl implements Service {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User Saveuser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUsername());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(userDto.getPassword()));
        user=userRepository.save(user);
        List<Role> roleList=new LinkedList<>();
        userDto.getRoles().stream().forEachOrdered(role -> {
            Role rol=new Role();
            rol.setRoleName(role.getRoleName());
            roleList.add(rol);
        });
        user.setRolelist(roleList);
        return user;
    }

    @Override
public UserSignupRequestVO logOfUser(UserSignupRequestVO userSignupRequestVO) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        List<Role> roles = new LinkedList<>();
        try {
            Optional<User> user = userRepository.findByUserName(userSignupRequestVO.getUserName());
            boolean status = bCrypt.matches(userSignupRequestVO.getPassword(), user.get().getPassword());
            if (user.isPresent() && status == true) {
                user.get().getRolelist().stream().forEachOrdered(role -> {
                    Role rol = new Role();
                    rol.setRoleName(role.getRoleName());
                    roles.add(role);
                });
                String Token = JwtUtil.generateToken(Constants.SIGNIN_KEY, user.get().getId(), "user", user.get().getUserName(), roles);
                userSignupRequestVO.setUserName(user.get().getUserName());
                userSignupRequestVO.setId(user.get().getId());
                userSignupRequestVO.setJwtToken(Token);
            }
        } catch (Exception e) {
            e.printStackTrace(); }
        return userSignupRequestVO;
    }

    public UserDetails loadByUserName(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByUserName(username);
        List<Role> roles = new LinkedList<>();
        if (userDetail == null) {
            throw new UsernameNotFoundException("user not found");}
        else{
            userDetail.get().getRolelist().stream().forEachOrdered(role -> {
                Role rol = new Role();
                rol.setRoleName(role.getRoleName());
                roles.add(role);
    });
            return new org.springframework.security.core.userdetails.User(userDetail.get().getUserName(), userDetail.get().getPassword(), getAuthority(roles));
        }
    }
    private List getAuthority(List<Role> role){
        List authorities=new ArrayList();
        role.stream().forEachOrdered(role1 -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +role1.getRoleName()));
        });
        return authorities;
    }
}

