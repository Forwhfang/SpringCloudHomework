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
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;
import java.io.File;
import java.nio.file.Files;

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

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        try{
            bookService.delete(id);
            String result = "{\"code\": 0,\"msg\":\"删除成功\"}";
            return result;
        } catch (Exception e) {
            String result = "{\"code\": 500,\"msg\":\"system error\"}";
            return result;
        }
    }

    @PutMapping("/update")
    @CrossOrigin
    @ResponseBody
    public String update(@RequestBody BookForm form) {
        try{
            String newUrl = handleBase64(form.getUrl());
            form.setUrl(newUrl);
            BookVO bookVO = bookService.save(form);
            String result = "{\"code\": 0,\"msg\": \"success\"}";
            return result;
        } catch (DataIntegrityViolationException e){
            String result = "{\"code\": 400,\"msg\":\"必填字段不可为空\"}";
            return result;
        }catch (Exception e){
            String result = "{\"code\": 500,\"msg\":\"system error\"}";
            return result;
        }
    }

    @GetMapping("/findById/{id}")
    @CrossOrigin
    @ResponseBody
    public BookVO findById(@PathVariable("id") Integer id) {
        BookVO bookVO = bookService.findById(id);
        // 转义本地文件
        bookVO.setUrl(handleLocalImage(bookVO.getUrl()));
        return bookVO;
    }

    @GetMapping("/findAll")
    @CrossOrigin
    @ResponseBody
    public Object findAll() {
        try{
            List<BookVO> bookVOList = bookService.findAll();
            // 转义本地文件
            Iterator iter = bookVOList.iterator();
            while (iter.hasNext()){
                BookVO temp = (BookVO) iter.next();
                temp.setUrl(handleLocalImage(temp.getUrl()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("data", bookVOList);
            map.put("code", 0);
            map.put("msg", "获取书籍列表成功");
            return map;
        } catch(Exception e) {
            String result = "{\"code\": 500,\"msg\":\"system error\"}";
            return result;
        }
    }

    @PostMapping("/add")
    @CrossOrigin
    @ResponseBody
    public Object add(@RequestBody BookForm form){
        String originUrl = form.getUrl();
        String newUrl = handleBase64(form.getUrl());
        form.setUrl(newUrl);
        try{
            BookVO bookVO = bookService.save(form);
            bookVO.setUrl(originUrl);
            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("msg","添加成功");
            map.put("book",bookVO);
            return map;
        } catch (DataIntegrityViolationException e){
            String result = "{\"code\": 400,\"msg\":\"必填字段不可为空\"}";
            return result;
        } catch (Exception e){
            String result = "{\"code\": 500,\"msg\":\"system error\"}";
            return result;
        }
    }

    private String handleBase64(String requestImg) {
        if (requestImg.indexOf("data:image/") != -1) {
            int firstIndex = requestImg.indexOf("data:image/") + 11;
            int index1 = requestImg.indexOf(";base64,");
            String type = requestImg.substring(firstIndex, index1);
            String fileName = "D:/uploadFiles/" + UUID.randomUUID().toString() + "." + type;
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

    private String handleLocalImage(String imagePath){
        // not local file
        if (imagePath.indexOf("/images.do?src=") == -1){
            return imagePath;
        }
        // 获取文件路径
        int firstIndex = imagePath.indexOf("/images.do?src=") + "/images.do?src=".length();
        String filePath = imagePath.substring(firstIndex);
        System.out.println(filePath);
        File file = new File(filePath);
        InputStream os = null;
        try{
            // 读取图片并解码
            os = new FileInputStream(file);
            BASE64Encoder encoder = new BASE64Encoder();
            byte[] imageRaw = Files.readAllBytes(file.toPath());
            String imageBase64 = encoder.encode(imageRaw).replace("\r\n","");
            System.out.println("encoded image: " + imageBase64);

            // 构造头部并返回
            String type = imagePath.substring(imagePath.lastIndexOf(".") + 1);
            return "data:image/" + type + ";base64," + imageBase64;
        } catch (FileNotFoundException e){
            System.out.println("file " + filePath + "not exist");
        } catch (Exception e){
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
        // 转码失败
        return imagePath;
    }


}
