#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

//String locationString = "location";
int buttonState = 0;         // current state of the button
int lastButtonState = 0;     // previous state of the button
const String IP = "192.168.31.181";
const int PORT = 3000;
const String TYPE = "magnet";

WiFiClient client;


String setupWifiManager() {
  WiFiManager wifiManager;

  //uncomment to reset saved settings
  wifiManager.resetSettings();

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

void connectToServer(String location) {
  while (true) {
    Serial.println("connecting to server")
    client.connect(IP, PORT);
    client.print(ESP.getChipId());
    client.print("|");
    client.print(location);
    client.print("|");
    client.print(TYPE);
    client.println();
    if (client.connected()) break;
    delay(10000);
  }
  Serial.println("connected to server")
}

void reconnectToServer() {
  while (true) {
    Serial.println("connecting to server")
    client.connect(IP, PORT);
    client.print(ESP.getChipId());
    if (client.connected()) break;
    delay(10000);
  }
  Serial.println("connected to server")
}






void setup() {
  // put your setup code here, to run once:
  pinMode(4, OUTPUT);
  pinMode(0, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);

  //start serial for debugging
  Serial.begin(115200);

  //method for easy connection to a wifi
  String location = setupWifiManager();

  connectToServer(location);
}

void loop() {
  while (client.connected()) {
    buttonState = digitalRead(0);
    if (buttonState != lastButtonState) {
      if (buttonState == LOW) {
        proximity.println("on");
        Serial.println("button on");
      } else {
        Serial.println("button off");
      }
    }
    lastButtonState = buttonState;


    if (client.available() > 0) {
      String message = client.readString();
      Serial.println("Server: " + s2);
      if (message.equals("ledOn")) {
        digitalWrite(4, HIGH);
        digitalWrite(LED_BUILTIN, LOW);
      } else if (message.equals("ledOff")) {
        digitalWrite(4, LOW);
        digitalWrite(LED_BUILTIN, HIGH);
      }
    }
  }
  reconnectToServer();
}
