package com.wondersgroup.springbootelasticsearchdemo;

import com.wondersgroup.springbootelasticsearchdemo.esrepository.BookRepository;
import com.wondersgroup.springbootelasticsearchdemo.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   //不用junit而用SpringRunner驱动器，这样可以在测试期间很方便的类似编码一样进行自动注入容器等功能
@SpringBootTest
public class SpringbootelasticsearchdemoApplicationTests {

    @Autowired
    BookRepository bookRepository;

    //向es写入数据
    @Test
    public void contextLoads() {
        Book book=new Book();
        book.setId(1);
        book.setBookName("西游记大大");
        book.setAuthor("吴承恩");
        bookRepository.save(book);
    }

    //验证自定义方法
    @Test
    public void Test01() {
        for (Book book : bookRepository.findByBookNameLike("游")) {
            System.out.println(book);
        }

    }

}