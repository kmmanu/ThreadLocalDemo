package org.manu.threadlocal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThreadlocalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadlocalDemoApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<UserNameFilter> userNameFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        UserNameFilter userNameFilter = new UserNameFilter();

        filterRegistrationBean.setFilter(userNameFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1); //set precedence
        return filterRegistrationBean;
    }
}
