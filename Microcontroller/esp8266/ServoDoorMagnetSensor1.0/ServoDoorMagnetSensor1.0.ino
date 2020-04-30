d#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager
#include <Servo.h>
//String locationString = "location";
int buttonState = 0;         // current state of the button
int lastButtonState = 0;     // previous state of the button
const String IP = "83.254.129.68";
const int PORT = 40000;
const String TYPE = "magnet";
const int servo = 4;
const int magnet = 0;
const int led = 13;
int ledState = LOW;
unsigned long preMillis = 0;
unsigned long ms;
unsigned long beatMs;
unsigned long preBeat = 0;
unsigned long connectMs;
unsigned long preConnect = 0;
unsigned long reconnectMs;
unsigned long preReconnect = 0;

Servo myservo;  // create servo object to control a servo

WiFiClient client;


String setupWifiManager() {
  WiFiManager wifiManager;

  //uncomment to reset saved settings
  //wifiManager.resetSettings();

  //Parameter for configuring the location at the same time as wifi
  WiFiManagerParameter location("location", "location", "", 40);
  wifiManager.addParameter(&location);

  //fetches ssid and pass from eeprom and tries to connect
  //if it does not connect it starts an access point with the specified name
  //and goes into a BLOCKING loop awaiting configuration
  wifiManager.autoConnect("ConnectToSetupSecuritySystem");

  //if you get here you have connected to the WiFi
  Serial.println("connected to wifi");
  Serial.println(location.getValue());
  return location.getValue();
}
void ledBlink() {
  ms = millis();
  if ((ms - preMillis) >= 500 ) {
    preMillis = ms;
    if (ledState == LOW) {
      ledState = HIGH;
    } else {
      ledState = LOW;
    } digitalWrite(led, ledState);
  }
}
void heartBeat() {
  beatMs = millis();
  if ((beatMs - preBeat) >= 1000 ) {
    preBeat = beatMs;
    client.println("heartbeat");
    Serial.println("heartbeat");
  }
}

void connectToServer(String location) {
  while (true) {
    ledBlink();
    connectMs = millis();
    if ((connectMs - preConnect) >= 5000 ) {
      preConnect = connectMs;
      Serial.println("connecting to server");
      client.connect(IP, PORT);
      client.print(ESP.getChipId());
      client.print("|");
      client.print(TYPE);
      client.print("|");
      client.print(location);
      client.println();
    }
    yield();
    if (client.connected()) break;
  }
  Serial.println("connected to server");
}

void reconnectToServer() {
  while (true) {
    ledBlink();
    reconnectMs = millis();
    if ((reconnectMs - preReconnect) >= 5000) {
      preReconnect = reconnectMs;
      Serial.println("reconnecting to server");
      client.connect(IP, PORT);
      client.print(ESP.getChipId());
      client.print("|");
      client.println(TYPE);
    }
    yield();
    if (client.connected()) break;
    //delay(5000);
  }
  Serial.println("connected to server");
}




void setup() {

  pinMode(magnet, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(led, OUTPUT);
  myservo.attach(servo);  // attaches the servo on GIO2 to the servo object


  //start serial for debugging
  Serial.begin(115200);
  client.setTimeout(250);

  //method for easy connection to a wifi
  String location = setupWifiManager();

  connectToServer(location);
}

void loop() {
  if (client.connected()) {
    heartBeat();
    digitalWrite(led, HIGH);
    buttonState = digitalRead(magnet);
    if (buttonState != lastButtonState) {
      if (buttonState == LOW) {
        client.println("off");
        Serial.println("off");
      } else {
        Serial.println("on");
        client.println("on");

      }
    }
    lastButtonState = buttonState;


    if (client.available() > 0) {
      char message = client.read();
      //String message = client.readString();
      Serial.println(message);
      if (message == 'c') {
        myservo.write(90);
      } else if (message == 'o') {
        myservo.write(0);
      }
    }
  } else reconnectToServer();

}
