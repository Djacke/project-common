/**
 * 
 */
package com.project.common.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.common.config.PropertyConfiger;

/**
 * kafka生产者工具类
 * 
 * @author chenjq5
 * 
 */
public class KafkaProducer {
	static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
	private Producer<String, String> inner;
	private final static String KAFKA_CONFIG_FILE = "config/kafka-producer";

	public KafkaProducer() {
		try {
			Properties props = PropertyConfiger.loadProperties(KAFKA_CONFIG_FILE);
			initKafkaProducer(props);
		} catch (Exception e) {			
			LOG.error(e.getMessage(), e);
		}
	}
	
	public KafkaProducer(Properties props){
		initKafkaProducer(props);
	}
	
	private void initKafkaProducer(Properties props){
		final ProducerConfig config = new ProducerConfig(props);
		inner = new Producer<String, String>(config);
	}

	/**
	 * 发送消息	
	 * 
	 * @param topicName
	 * @param message
	 */
	public void send(String topicName, String message) {
		if (topicName == null || message == null) {
			return;
		}
		KeyedMessage<String, String> km = new KeyedMessage<String, String>(
				topicName, message);
		inner.send(km);
	}

	/**
	 * 分区下发送消息
	 * 
	 * @param topicName
	 * @param message
	 * @param key 分区id
	 * 如果具有多个partitions,请使用new KeyedMessage(String topicName,K key,V value).
	 */
	public void send(String topicName, String message, String key) {
		if (topicName == null || message == null) {
			return;
		}
		KeyedMessage<String, String> km = new KeyedMessage<String, String>(
				topicName, key, message);	
		inner.send(km);
	}

	/**
	 * 批量发送消息
	 * 
	 * @param topicName
	 * @param messages
	 */
	public void send(String topicName, Collection<String> messages) {
		if (topicName == null || messages == null) {
			return;
		}
		if (messages.isEmpty()) {
			return;
		}
		List<KeyedMessage<String, String>> kms = new ArrayList<KeyedMessage<String, String>>();
		for (String entry : messages) {
			KeyedMessage<String, String> km = new KeyedMessage<String, String>(
					topicName, entry);
			kms.add(km);
		}
		inner.send(kms);
	}

	/**
	 * 批量发送消息，分区情况下
	 * 
	 * @param topicName
	 * @param messages
	 */
	public void send(List<KeyedMessage<String, String>> messages) {
		if (messages == null) {
			return;
		}
		if (messages.isEmpty()) {
			return;
		}
		inner.send(messages);
	}

	public void close() {
		inner.close();
	}

}
