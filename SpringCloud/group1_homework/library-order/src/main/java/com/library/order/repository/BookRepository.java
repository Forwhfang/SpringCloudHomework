package com.library.order.repository;

import com.library.order.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Group One
 * @package com.library.order.repository
 * @date 2020/6/25
 * @brief
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
