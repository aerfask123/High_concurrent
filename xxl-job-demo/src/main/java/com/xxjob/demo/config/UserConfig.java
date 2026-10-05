package com.xxjob.demo.config;

import com.xxjob.demo.dto.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public Address address() {
        return new Address("广东省", "深圳市", "福田区", "龙岗大道");
    }

//    @Bean
//    public CarInterface car() {
//        System.out.println("car created");
//        return new Porsche();
//    }
//
//    @Bean
//    @Primary
//    public CarInterface car2() {
//        System.out.println("car2 created");
//        return new Porsche();
//    }
}
