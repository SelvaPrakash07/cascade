package com.example.firstapp.Model;


import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade =CascadeType.ALL)
    @JoinTable(name="userrole", joinColumns={@JoinColumn(name="user_id_fk", referencedColumnName="user_id")}
            , inverseJoinColumns={@JoinColumn(name="role_id_fk", referencedColumnName="id")})
    private List<Role> rolelist;


    public User(User user) {
        this.id = user.getId();
        this.rolelist = user.getRolelist();
        this.userName = user.getUserName();
    }

}

