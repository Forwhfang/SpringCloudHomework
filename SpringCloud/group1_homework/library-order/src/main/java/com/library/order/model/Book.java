package com.library.order.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Group One
 * @package com.library.order.model
 * @date 2020/6/25
 * @brief
 */
@Data
@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "goods_name", nullable = false, length = 50)
    private String goodsName;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "count", nullable = false, length = 50)
    private Integer count;

    @Column(name = "money", nullable = false, length = 50)
    private Double money;

}
