package edu.miu.cs.cs425.carrentalswe.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Role name connot be blank")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
