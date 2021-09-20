package com.epam.rd.java.basic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
@Entity
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "count", nullable = false, columnDefinition="INT UNSIGNED")
    private Integer count;

    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updateTime;

}
