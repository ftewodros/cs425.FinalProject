package edu.miu.cs.cs425.carrentalswe.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false, unique = true, name = "user_name")
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Size(min = 8)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="users_roles", joinColumns = {
            @JoinColumn(name="user_id",referencedColumnName = "userId")
    },
            inverseJoinColumns = {
            @JoinColumn(name="role_id", referencedColumnName = "roleId")
            }
    )
    private List<Role> roles;

    @OneToOne(mappedBy = "user")
    private ExternalUser externalUser;
}
