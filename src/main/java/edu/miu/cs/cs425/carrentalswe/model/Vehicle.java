package edu.miu.cs.cs425.carrentalswe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", nullable = false)
    private long vehicleId;

    @Column(name = "model")
    private String model;


    @Column(name = "plateNumber", unique = true)
    private String plateNumber;

    @Column(name = "year")
    private Integer year;

    @Column(name = "status")
    private String status; // (booked, available, unavailable)

    // @Column(name = "photo")
    // private String photo;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    ExternalUser externalUser;
}
