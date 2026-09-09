package com.example.TicketBooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookings")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_time", updatable = false)
    private LocalDateTime bookingTime;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookingSeat> bookingSeats = new HashSet<>();

    @PrePersist
    public void onCreate() {
        this.bookingTime = LocalDateTime.now();

        if (this.status == null) {
            this.status = Status.PENDING;
        }
    }

    public void addBookingSeat(BookingSeat seat) {
        this.bookingSeats.add((seat));
        seat.setBooking(this);
    }

    public void removeBookingSeat(BookingSeat seat) {
        this.bookingSeats.remove(seat);
        seat.setBooking(null);
    }
}
