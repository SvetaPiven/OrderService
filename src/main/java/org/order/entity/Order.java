package org.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cache.annotation.EnableCaching;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity implements Serializable{

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "delivery_datetime", nullable = false)
    private Instant deliveryDatetime;

    @Column(name = "delivery_address", nullable = false, length = 256)
    private String deliveryAddress;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Good> goods = new ArrayList<>();

}