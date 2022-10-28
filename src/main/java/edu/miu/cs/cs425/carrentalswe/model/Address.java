package edu.miu.cs.cs425.carrentalswe.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address implements Serializable {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}