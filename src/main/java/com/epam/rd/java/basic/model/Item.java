package com.epam.rd.java.basic.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "item_details",
            joinColumns = {@JoinColumn (name = "item_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn (name = "category_id", referencedColumnName = "id")}
    )
    private Set<Category> categorySet = new HashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Cart> carts = new HashSet<>();

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ItemDetails itemDetails;


    @Column(name = "count", columnDefinition = "INT UNSIGNED")
    private Integer count;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @Column(name = "update_time", columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updateTime;

}
