
package com.project.common.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.log4j.Logger;

import com.project.common.config.PropertyConfiger;

/**
 * 
 * @author chenjq5
 *
 */
public class KafkaConsumer {
	static final Logger LOG = Logger.getLogger(KafkaConsumer.class);
	private ConsumerConnector consumer;
	private ExecutorService executor;
    private KafkaMessageListener listener=null;
    
    private final static String CONFIG_FILE = "config/kafka-consumer";
    
    public KafkaConsumer(KafkaMessageListener listener) {		
		try {
			Properties props = PropertyConfiger.loadProperties(CONFIG_FILE);			
			consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
			this.listener=listener;
		} catch (Exception ex) {			
			LOG.error(ex.getMessage(), ex);
		}
    }
    
	public void shutdown() {
		if (consumer != null)
		{
			consumer.shutdown();
		}
		if (executor != null)
		{
			executor.shutdown();
		}
	}
	public void consume(final String topicName, final int a_numThreads) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topicName, new Integer(a_numThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topicName);

		executor = Executors.newFixedThreadPool(a_numThreads);

		for (final KafkaStream stream : streams) {
			executor.submit(
					new Runnable() {
						@Override
						public void run(){
							ConsumerIterator<byte[], byte[]> it = stream.iterator();
							while (it.hasNext()) {
								try {
									String msg = new String(it.next().message(),"UTF-8");
									LOG.info("on message ="+msg);
									listener.onMessage(msg);
									System.out.println(msg);
								} catch (Exception e) {
									LOG.error(e.getMessage(), e);
								}
							}
						}
					});
		}
	}
}
