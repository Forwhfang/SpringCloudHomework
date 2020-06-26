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
    private String imgUrl;
    private String name;
    private double star;
    private String quotes;
    private String author;
    private String translator;
    private String pubdate;
    private String press;

    public String getUrl() {
        return imgUrl;
    }
    public void setUrl(String newUrl) {
        this.imgUrl = newUrl;
    }
}
