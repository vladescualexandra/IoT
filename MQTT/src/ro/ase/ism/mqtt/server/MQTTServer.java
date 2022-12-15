package ro.ase.ism.mqtt.server;

import org.eclipse.paho.client.mqttv3.*;
import ro.ase.ism.mqtt.common.Env;
import ro.ase.ism.mqtt.common.Utils;

import java.util.Scanner;

public class MQTTServer {

    public static void main(String[] args) {
        try {
            IMqttClient publisher = new MqttClient(Env.SERVER, "server-id");

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            publisher.connect(options);

            MqttMessage message = new MqttMessage();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("-".repeat(20));
                System.out.print("Enter your payload: ");
                String payload = scanner.nextLine();

                String encryptedPayload = Utils.encrypt(payload);
                message.setPayload(encryptedPayload.getBytes());
                message.setRetained(true);
                publisher.publish(Env.PoC_TOPIC, message);
                System.out.println("Message published: " + payload);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
