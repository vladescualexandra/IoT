package ro.ase.ism.mqtt.common;


public class Env {

    // Just for demonstration purposes.
    public static final String SYMMETRIC_KEY = "symmetricKey-PoC";

    public static final String SERVER = "tcp://test.mosquitto.org:1883";
    public static final String PoC_TOPIC = "PoCTopic";

    /*
        https://test.mosquitto.org/
        Port 1883 : MQTT, unencrypted, unauthenticated
     */
}
