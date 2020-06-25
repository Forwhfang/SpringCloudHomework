package com.library.order.form;

import lombok.Data;

/**
 * @author Group One
 * @package com.library.order.form
 * @date 2020/6/25
 * @brief
 */
@Data
public class BookForm {
    private Integer id;

    private String goodsName;

    private String address;

    private String name;

    private String phone;

    private Integer count;

    private Double money;
}
