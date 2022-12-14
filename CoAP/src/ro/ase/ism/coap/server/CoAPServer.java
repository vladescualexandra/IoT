package ro.ase.ism.coap.server;

import org.ws4d.coap.connection.BasicCoapChannelManager;
import org.ws4d.coap.interfaces.*;
import org.ws4d.coap.messages.CoapMediaType;
import org.ws4d.coap.messages.CoapResponseCode;
import ro.ase.ism.coap.common.Env;
import ro.ase.ism.coap.common.Utils;

import java.nio.charset.StandardCharsets;

public class CoAPServer implements CoapServer {
    private static CoAPServer INSTANCE = null;


    private CoAPServer() {
    }

    public static CoAPServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoAPServer();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println("Start CoAP Server on port: " + Env.PORT);
        CoAPServer server = CoAPServer.getInstance();

        CoapChannelManager channelManager = BasicCoapChannelManager.getInstance();
        channelManager.createServerListener(server, Env.PORT);
    }

    @Override
    public CoapServer onAccept(CoapRequest request) {
        System.out.println(Utils.ANSI_GREEN + "Connection accepted." + Utils.ANSI_RESET);
        return this;
    }

    @Override
    public void onRequest(CoapServerChannel channel, CoapRequest request) {
        try {
            System.out.println("Received message: " + request.toString() + " URI: " + request.getUriPath());
            String payload = new String(request.getPayload(), StandardCharsets.UTF_8);

            String decryptedPayload = Utils.decrypt(payload);

            System.out.println("Received Payload: " + decryptedPayload);

            CoapMessage response = channel.createResponse(request,
                    CoapResponseCode.Content_205);
            response.setContentType(CoapMediaType.text_plain);


            if (request.getObserveOption() != null) {
                System.out.println("Client wants to observe this resource.");
            }

            response.setObserveOption(1);
            channel.sendMessage(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSeparateResponseFailed(CoapServerChannel channel) {
        System.out.println(Utils.ANSI_RED + "Separate response transmission failed." + Utils.ANSI_RESET);
    }
}
