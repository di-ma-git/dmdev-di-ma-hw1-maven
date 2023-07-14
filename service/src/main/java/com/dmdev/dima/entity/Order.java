package com.dmdev.dima.entity;

import com.dmdev.dima.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private Status status;
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private LocalDateTime orderDate;
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pay_date")
    private LocalDateTime payDate;







}
