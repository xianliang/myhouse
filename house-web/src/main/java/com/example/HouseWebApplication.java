package com.example;

import com.example.kafka.KafkaMsgProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.example.*")
@EnableAsync
public class HouseWebApplication  {
    @Autowired
    private KafkaMsgProducer kafkaMsgProducer;
    @PostConstruct
    public void init(){
        for(int i = 0; i < 10; i++){
            kafkaMsgProducer.sendMessage(String.valueOf(i));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(HouseWebApplication.class, args);
    }


}
