package org.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "changed")
    private Instant changed;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @NotNull
    @Column(name = "delivery_datetime", nullable = false)
    private Instant deliveryDatetime;

    @Size(max = 256)
    @NotNull
    @Column(name = "delivery_address", nullable = false, length = 256)
    private String deliveryAddress;

    @NotNull
    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus = false;

    @OneToMany(mappedBy = "order")
    private Set<Good> goods = new LinkedHashSet<>();

}