package com.library.order.service;

import com.library.order.dto.BookVO;
import com.library.order.form.BookForm;
import com.library.order.model.Book;
import com.library.order.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Group One
 * @package com.library.order.service
 * @date 2020/6/25
 * @brief
 */
@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookVO save(BookForm form) {
        Book book = new Book();
        BeanUtils.copyProperties(form, book);
        book = bookRepository.saveAndFlush(book);
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book, bookVO);
        return bookVO;
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookVO update(BookForm form) {
        Book book = new Book();
        BeanUtils.copyProperties(form, book);
        book = bookRepository.saveAndFlush(book);
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book, bookVO);
        return bookVO;
    }

    @Override
    public BookVO findById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(book.get(), bookVO);
        return bookVO;
    }

    @Override
    public List<BookVO> findAll() {
        List<Book> bookList = bookRepository.findAll();
        List<BookVO> bookVOList = new ArrayList<BookVO>();
        if (null != bookList && bookList.size() > 0) {
            bookVOList = bookList.stream().map(
                    e -> {
                        BookVO bookVO = new BookVO();
                        BeanUtils.copyProperties(e, bookVO);
                        return bookVO;
                    }
            ).collect(Collectors.toList());
        }
        return bookVOList;
    }
}
