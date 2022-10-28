package edu.miu.cs.cs425.carrentalswe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "category_name", nullable = false)
    @NotNull(message = "*Please provide category name")
    private String name;

    @Column(name = "seats")
    @NotNull(message = "*Please provide number of seats")
    private Integer seats;

    @Column(name = "doors")
    @NotNull(message = "*Please provide number of doors")
    private Integer doors;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList();

}
