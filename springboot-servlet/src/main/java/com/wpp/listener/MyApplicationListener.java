package com.wpp.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: jackgeeks
 * @ProjectName: springboot-llf
 * @Package: com.wpp.springbootllf.listener
 * @ClassName: MyApplicationListener
 * @Description: @todo
 * @CreateDate: 2020/5/1 0:25
 * @Version: 1.0
 */
@Slf4j
@Component
public class MyApplicationListener {
        @EventListener
        public void onApplicationEvent(String event) {
                log.info("MyApplicationListener接收到事件"+event);
                }
}
