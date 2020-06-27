package com.library.order.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.library.order.dto.BookVO;
import com.library.order.form.BookForm;
import com.library.order.service.BookService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

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
    @CrossOrigin
    @ResponseBody
    public String update(@RequestBody BookForm form) {
        try{
            String newUrl = parseImg(form.getUrl());
            form.setUrl(newUrl);
            BookVO bookVO = bookService.update(form);
            String result = "{\"code\": 0,\"msg\": \"success\"}";
            return result;
        } catch (DataIntegrityViolationException e){
            String result = "{\"code\": 400,\"msg\":必填字段不可为空}";
            return result;
        }catch (Exception e){
            String result = "{\"code\": 500,\"msg\":system error}";
            return result;
        }
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

    public String parseImg(String requestImg) {
        if (requestImg.indexOf("data:image/") != -1) {
            int firstIndex = requestImg.indexOf("data:image/") + 11;
            int index1 = requestImg.indexOf(";base64,");
            String type = requestImg.substring(firstIndex, index1);
            String fileName = "E:/uploadFiles/" + UUID.randomUUID().toString() + "." + type;
            //开始转码，然后将当前文件写入数据。
            System.out.println(fileName);
            BASE64Decoder decoder = new BASE64Decoder();
            OutputStream os = null;
            try {
                String imgsrc = StringUtils.substringBefore(requestImg.substring(requestImg.indexOf(";base64,") + 8), "\"");
                byte[] bytes = decoder.decodeBuffer(imgsrc);
                //替换之前的src中base64数据为servlet请求
                requestImg = requestImg.replace("data:image/" + type + ";base64," + imgsrc, "/images.do?src=" + fileName);
                File file = new File(fileName);
                //获取父目录
                File fileParent = file.getParentFile();
                //判断是否存在
                if (!fileParent.exists()) {
                    //创建父目录文件
                    fileParent.mkdirs();
                }
                file.createNewFile();
                os = new FileOutputStream(file);
                os.write(bytes);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return requestImg;

    }

}
