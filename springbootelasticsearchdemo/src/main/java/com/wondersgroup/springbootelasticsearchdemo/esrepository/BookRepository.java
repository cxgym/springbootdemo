package com.wondersgroup.springbootelasticsearchdemo.esrepository;

import com.wondersgroup.springbootelasticsearchdemo.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {//这里的泛型Integer是Book的主键类型
  //除了继承ElasticsearchRepository里面的index和search等方法，也支持自定义方法
  //方法名的命名参考官网：
  //https://docs.spring.io/spring-data/elasticsearch/docs/4.0.3.RELEASE/reference/html/#preface.metadata
  //-->文档节点：A list of supported keywords for Elasticsearch is shown below.
  public List<Book> findByBookNameLike(String bookName);
}
