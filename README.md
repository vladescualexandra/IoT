# CoAP-implementation

#### What is the meaning of CoAP?
Constrained Application Protocol (CoAP) is a specialized Internet application protocol for constrained devices, as defined in RFC 7252.

#### How CoAP is used in IoT?
CoAP functions as a sort of HTTP for restricted devices, enabling equipment such as sensors or actuators to communicate on the IoT. These sensors and actuators are controlled and contribute by passing along their data as part of a system.

##### CoAP Implementation
CoAP Security Scheme PoC Implementation for Authenticity and Confidentiality
- Both, the CoAP Server and Client should have security of the payload - you may use Lightweight Crypto for Payload in any format of the following (e.g. JSON or ASN.1 DER or CBOR/COSE compliant): IPSO, OMA, etc.
- As COAP implementation, any Java, Kotlin, C, C#, Python, JavaScript/ECMAScript/node.js, etc. library can be used.

#### What is the meaning of MQTT?
MQTT stands for Message Queuing Telemetry Transport. It is a lightweight messaging protocol for use in cases where clients need a small code footprint and are connected to unreliable networks or networks with limited bandwidth resources.

#### How MQTT is used in IoT?
MQTT uses your existing Internet home network to send messages to your IoT devices and respond to the messages. At the core of MQTT is the MQTT broker and the MQTT clients. The broker is responsible for dispatching messages between the sender and the rightful receivers.

#### MQTT Implementation
MQTT Security Scheme PoC Implementation for Authenticity and Confidentiality
- Both clients should have security of the payload: publisher and subscriber
- As MQTT Broker can be used a local deployment or test.mosquitto.org:1883
