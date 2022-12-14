package ro.ase.ism.coap.client;

import org.ws4d.coap.connection.BasicCoapChannelManager;
import org.ws4d.coap.interfaces.*;
import org.ws4d.coap.messages.CoapRequestCode;
import ro.ase.ism.coap.common.Env;
import ro.ase.ism.coap.common.Utils;

import java.net.InetAddress;
import java.util.Scanner;

public class CoAPClient implements CoapClient {
    private static final String PoC_ENDPOINT = "/security/";

    CoapChannelManager channelManager = null;
    CoapClientChannel clientChannel = null;

    public static void main(String[] args) {
        System.out.println("Start CoAP Client: " + Env.SERVER_ADDRESS + ":" + Env.PORT);

        CoAPClient client = new CoAPClient();
        client.channelManager = BasicCoapChannelManager.getInstance();
        client.runPayloadSecureRequest();
    }

    private void runPayloadSecureRequest() {
        try {
            // Connect to the server.
            clientChannel = channelManager.connect(this,
                    InetAddress.getByName(Env.SERVER_ADDRESS), Env.PORT);

            // Build CoAP Request.
            CoapRequest request = clientChannel.createRequest(true, CoapRequestCode.POST);
            request.setUriPath(PoC_ENDPOINT);

            while (true) {
                Thread.sleep(1000);

                Scanner scanner = new Scanner(System.in);
                System.out.println("-".repeat(20));
                System.out.print("Enter your payload: ");
                String payload = scanner.nextLine();

                String encryptedPayload = Utils.encrypt(payload);
                System.out.println("Encrypted Payload: " + encryptedPayload);
                request.setPayload(encryptedPayload);

                clientChannel.sendMessage(request);
                System.out.println("Request sent: " + request);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onConnectionFailed(CoapClientChannel channel, boolean notReachable, boolean resetByServer) {
        System.out.println(Utils.ANSI_RED + "Connection Failed." + Utils.ANSI_RESET);
    }

    @Override
    public void onResponse(CoapClientChannel channel, CoapResponse response) {
        System.out.println(Utils.ANSI_GREEN + "Response received: " + response + Utils.ANSI_RESET);
    }
}
