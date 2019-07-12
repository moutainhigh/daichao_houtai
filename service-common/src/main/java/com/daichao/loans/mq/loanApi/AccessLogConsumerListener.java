package com.daichao.loans.mq.loanApi;

import com.daichao.common.config.QueneVariable;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.rabbitmq.client.QueueingConsumer;

/**
 * 接口访问日志消息消费者
 * 
 * @author likang
 */
@Service("accessLogConsumerListener")
public class AccessLogConsumerListener implements ChannelAwareMessageListener {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccessLogExecuter accessLogExecuter;

	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("消息消费者：{}", new String(message.getBody()));
		// 该消息的投递标识
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		// multiple是否批量应答多个投递.true:将一次性拒绝所有小于deliveryTag的消息
		boolean multiple = false;
		// 消息确认机制：RabbitMQ会等待消费者显式发回ack信号后才从内存(和磁盘，如果是持久化消息的话)中移去消息。
		// 否则，RabbitMQ会在队列中消息被消费后立即删除它。
		// true-自动应答，false-关闭RabbitMQ的自动应答机制，改为手动应答ack，需要使用channel.ack、channel.nack、channel.basicReject
		// 进行消息应答
		// boolean autoAck = false ;
		try {
			// 接收到消息后，就会自动反馈一个消息给服务器
			// QueueingConsumer callback = new QueueingConsumer(channel);
			// channel.basicConsume(QueneVariable.LOG_QUENE_NAME, autoAck,
			// callback);
			// 消费信息处理
			accessLogExecuter.execute(message);
			logger.debug("queueName:[{}]; deliveryTag:[{}]", QueneVariable.LOG_QUENE_NAME, deliveryTag);
			// 在处理完消息时，返回应答状态
			// channel.basicAck(deliveryTag, multiple);
		} catch (Exception e) {
			// 被拒绝的消息是否重新入队列
			// boolean requeue = true ;
			// channel.basicNack(deliveryTag, multiple, requeue);
			logger.error("AccessLogQuene Consume fail:[{}]", e);
		} finally {
			// // 消息确认机制：RabbitMQ会等待消费者显式发回ack信号后才从内存(和磁盘，如果是持久化消息的话)中移去消息。
			// // 否则，RabbitMQ会在队列中消息被消费后立即删除它。
			// //
			// true-自动应答，false-关闭RabbitMQ的自动应答机制，改为手动应答ack，需要使用channel.ack、channel.nack、channel.basicReject
			// // 取消消息自动应答，进行手动应答
			// boolean autoAck = true;
			// // 接收到消息后，就会自动反馈一个消息给服务器
			// String queueName = msg.getQueueName();
			// QueueingConsumer callback = new QueueingConsumer(channel);
			// channel.basicConsume(queueName, autoAck, callback);
			// 该消息的投递标识
			// deliveryTag = message.getMessageProperties().getDeliveryTag();
			// multiple是否批量应答多个投递.true:将一次性拒绝所有小于deliveryTag的消息
			// multiple = false;
			channel.basicAck(deliveryTag, multiple);
		}
		// Thread.sleep(1000);
	}

}
