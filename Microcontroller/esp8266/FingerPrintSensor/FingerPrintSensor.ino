#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino
#include <Adafruit_Fingerprint.h>
#include <SoftwareSerial.h>

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

//SoftSerial use pins D6 (TX) and D5 (RX).
SoftwareSerial swSer(14, 12, false, 128);

//String locationString = "location";

const String IP = "83.254.129.68";
const int PORT = 40000;
const String TYPE = "fingerprint";
WiFiClient client;
Adafruit_Fingerprint finger = Adafruit_Fingerprint(&swSer);


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

  return location.getValue();
}

void connectToServer(String location) {
  while (true) {
    client.connect(IP, PORT);
    client.print(ESP.getChipId());
    client.print("|");
    client.print(TYPE);
    client.print("|");
    client.print(location);
    client.println();
    if (client.connected()) break;
    delay(10000);
  }
}

void reconnectToServer() {
  while (true) {
    client.connect(IP, PORT);
    client.print(ESP.getChipId());
    client.print("|");
    client.println(TYPE);

    if (client.connected()) break;
    delay(10000);
  }
}




void setup() {
  Serial.begin(115200);

  // set the data rate for the sensor serial port
  finger.begin(57600);

  //method for easy connection to a wifi
  String location = setupWifiManager();

  connectToServer(location);
}

void loop() {
  if (client.connected()) {
    
  } else reconnectToServer();
}
