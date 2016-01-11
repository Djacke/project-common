/**
 * 
 */
package com.project.common.kafka;


/**
 * @author chenjq5
 *
 */
public interface  KafkaMessageListener {
	public void onMessage(String msg);
}
