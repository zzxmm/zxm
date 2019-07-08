package com.shouzan.back.config;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**   
 * @ClassName:  ActiveMqConfiguration   
 * @Description:activemq 配置类
 * @author:
 * @date:   2018年2月11日 下午4:00:49   
 *    
 * @Copyright:2018
 *
 */

@Configuration
public class ActiveMqConfiguration {

	@Bean
    public Topic topic() {
        return new ActiveMQTopic("notice.PTP");
    }
}
