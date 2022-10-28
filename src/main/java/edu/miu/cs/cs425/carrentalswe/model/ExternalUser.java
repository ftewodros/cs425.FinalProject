package edu.miu.cs.cs425.carrentalswe.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "external_user")
public class ExternalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extuser_id", nullable = false)
    private long id;

    @Column(name = "approved")
    @NotNull
    private boolean approved;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "idImage_url",nullable = true, length = 64)
    private String idImage_url;

    @Column(name = "driverLicenceNumber")
    private String driverLicenceNumber;

    @Column(name = "dateOfBirth")
    @NotNull(message = "Date Opened cannot be Null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Embedded
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    User user;

    @OneToMany(mappedBy="externalUser")
    private List<Vehicle> vehicles = new ArrayList();
}