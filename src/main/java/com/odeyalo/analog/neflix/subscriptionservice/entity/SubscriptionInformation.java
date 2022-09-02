package com.odeyalo.analog.neflix.subscriptionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SubscriptionInformation {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String subscriptionId;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private LocalDate started; // When a subscription was started
    @Column(nullable = false)
    private LocalDate activeTo; // When subscription will expire

    public SubscriptionInformation(String userId, boolean isActive, LocalDate started, LocalDate activeTo) {
        this.userId = userId;
        this.isActive = isActive;
        this.started = started;
        this.activeTo = activeTo;
    }
}
