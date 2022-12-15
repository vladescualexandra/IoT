package ro.ase.ism.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import ro.ase.ism.mqtt.common.Env;
import ro.ase.ism.mqtt.common.Utils;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class MQTTClient {

    public static CountDownLatch signal;

    public static void main(String[] args) throws MqttException, InterruptedException {
        String subscriberId = UUID.randomUUID().toString();
        IMqttClient subscriber = new MqttClient(Env.SERVER, subscriberId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);
        System.out.println("Connected to server, client id: " + subscriberId);

        signal = new CountDownLatch(1000);

        subscriber.subscribe(Env.PoC_TOPIC, (topic, message) -> {
            byte[] payload = Base64.getDecoder()
                    .decode(message.getPayload());
            String decryptedPayload = Utils.decrypt(payload);

            System.out.println("Message from MQTTServer: " + decryptedPayload);
        });

        signal.await(10, TimeUnit.MINUTES);
    }
}