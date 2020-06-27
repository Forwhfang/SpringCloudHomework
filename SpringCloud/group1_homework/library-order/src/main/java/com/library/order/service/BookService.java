package com.library.order.service;

import com.library.order.dto.BookVO;
import com.library.order.form.BookForm;

import java.util.List;

/**
 * @author Group One
 * @package com.library.order.service
 * @date 2020/6/25
 * @brief
 */
public interface BookService {
    BookVO save(BookForm form);

    void delete(Integer id);

    BookVO findById(Integer id);

    List<BookVO> findAll();
}
