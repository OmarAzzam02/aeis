package org.aeis.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "HALLS")
@NamedQuery(name = "Hall.findAll", query = "SELECT h FROM Hall h")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "hall_seq")
    private Long id;

    @Column(name = "hall_name")
    private String name;
}
