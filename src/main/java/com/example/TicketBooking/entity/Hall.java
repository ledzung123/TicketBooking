package com.example.TicketBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "halls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats;

    @OneToMany(mappedBy = "hall")
    private Set<Showtime> showtime;
}
