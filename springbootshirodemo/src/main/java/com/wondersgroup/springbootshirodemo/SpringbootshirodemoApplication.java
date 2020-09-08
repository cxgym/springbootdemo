package com.wondersgroup.springbootshirodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class SpringbootshirodemoApplication {

    /**
     * 定制或者修改servlet容器
     * @return
     */
    /*@Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/404");
        ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/404");
        ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/404");
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage errorPage415 = new ErrorPage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        factory.addErrorPages(errorPage400, errorPage401, errorPage403, errorPage404, errorPage415, errorPage500);
        return factory;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SpringbootshirodemoApplication.class, args);
    }

}
