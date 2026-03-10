package com.ruoyi.common.core.mqtt;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MQTTClient implements InitializingBean {
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// 心跳30s
    public final static long RECONNECTION_ATTEMPT_MAX = 2;
    public final static long RECONNECTION_DELAY = 1000;
    public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M

    private FutureConnection connection;

    @Override
    public void afterPropertiesSet() throws Exception {
//        MQTT mqtt = new MQTT();
//        mqtt.setHost("192.168.0.129", 1883);
//        mqtt.setKeepAlive(KEEP_ALIVE);
//        mqtt.setCleanSession(CLEAN_START);
//        mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
//        mqtt.setReconnectDelay(RECONNECTION_DELAY);
//        mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
//        FutureConnection connection = mqtt.futureConnection();
//
//        Future<Void> f1 = connection.connect();
//        f1.await();
//        if (connection.isConnected()) {
//            this.connection = connection;
//            System.out.println("MQTT服务器连接成功");
//        } else {
//            throw new Exception("MQTT服务器连接失败");
//        }

        /*ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Future<byte[]> f2 = connection.subscribe(new Topic[]{new Topic(utf8("test"), QoS.AT_LEAST_ONCE)});
                Message message = null;
                while (true) {
                    try {
                        byte[] qoses = f2.await();
                        Future<Message> receive = connection.receive();
                        message = receive.await();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("<--:" + message.getPayloadBuffer().utf8());
                    message.ack();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String send = "Hello," + new Date();
                    System.out.println("-->:" + send);
                    Future<Void> f3 = connection.publish("test", send.getBytes(), QoS.AT_LEAST_ONCE, false);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    String send = "Hello," + new Date() + "," + i;
                    System.out.println("-->:" + send);
                    Future<Void> f3 = connection.publish("test2", send.getBytes(), QoS.AT_LEAST_ONCE, false);
                    i++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });*/
    }

    public FutureConnection getConnection() {
        return connection;
    }
}
