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
    // length = 50
    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "star", nullable = false, length = 10)
    private double star;

    @Column(name = "quotes", nullable = false, length = 250)
    private String quotes;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "translator", nullable = true, length = 50)
    private String translator;

    @Column(name = "pubdate", nullable = true, length = 50)
    private String pubdate;

    @Column(name = "press", nullable = true, length = 50)
    private String press;

}
