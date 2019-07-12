package com.daichao.loans.mq;

import com.daichao.common.utils.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理适配器，用于分发到具体实现
 * Created by zhangxiaolong on 2017/7/25.
 */
@Component("messageExecuteAdapter")
public class MessageExecuteAdapter {

    /**
     * 通过消费队列和路由key分发消息
     * @param message
     */
    public String execute(Message message){
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        if (StringUtils.equals(consumerQueue, "lendPayNotifyQuene1")){
//            lendPayNotifyExecuter.execute(message);
            return consumerQueue;
        }
        return consumerQueue;
    }


}
