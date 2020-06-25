package com.library.order.controller;

import com.library.order.dto.BookVO;
import com.library.order.form.BookForm;
import com.library.order.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Group One
 * @package com.library.order.controller
 * @date 2020/6/25
 * @brief
 */
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public BookVO save(@RequestBody BookForm form) {
        BookVO bookVO = bookService.save(form);
        return bookVO;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        bookService.delete(id);
        return "删除成功";
    }

    @PutMapping("/update")
    public BookVO update(@RequestBody BookForm form) {
        BookVO bookVO = bookService.update(form);
        return bookVO;
    }

    @GetMapping("/findById/{id}")
    public BookVO findById(@PathVariable("id") Integer id) {
        BookVO bookVO = bookService.findById(id);
        return bookVO;
    }

    @GetMapping("/findAll")
    public List<BookVO> findAll() {
        List<BookVO> bookVOList = bookService.findAll();
        return bookVOList;
    }
}
