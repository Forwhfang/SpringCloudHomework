package com.library.order.dto;

import lombok.Data;

/**
 * @author Group One
 * @package com.library.order.dto
 * @date 2020/6/25
 * @brief
 */
@Data
public class BookVO {
    private Integer id;

    private String goodsName;

    private String address;

    private String name;

    private String phone;

    private Integer count;

    private Double money;
}
