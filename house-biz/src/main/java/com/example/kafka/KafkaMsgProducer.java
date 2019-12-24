package com.example.kafka;

import com.alibaba.fastjson.JSON;
import com.example.common.model.Order;
import com.example.service.FileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author liangxianliang
 * @create 2019-12-20 11:53
 */
@Component
public class KafkaMsgProducer {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String orderId){
        Order order = new Order();
        order.setOrderId(orderId).setOrderContent("下单了").setCreateTime(new Date());
        kafkaTemplate.send("order-dispacher3",0,"1",JSON.toJSONString(order));
        kafkaTemplate.send("order-dispacher3", 1,"2",JSON.toJSONString(order));
        //kafkaTemplate.send("order-dispacher", JSON.toJSONString(order));
        logger.info("kafka消息发送成功"+order.toString());
    }

    @KafkaListener(topics = "order-dispacher3")
    public void readMessage(ConsumerRecord<String,String> consumerRecord){
        logger.info("消费消息："+consumerRecord.toString());
    }
}
