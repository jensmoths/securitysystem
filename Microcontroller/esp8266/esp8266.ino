#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

String locationString;


void setupWifiManager() {
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
  locationString = location.getValue();
}






void setup() {
  // put your setup code here, to run once:

  //start serial for debugging
  Serial.begin(115200);

  //method for easy connection to a wifi
  setupWifiManager();



}

void loop() {
  // put your main code here, to run repeatedly:
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  Serial.println("connecting");
  client.connect("192.168.31.181", 40000);
  client.println(ESP.getChipId());

  client.setTimeout(1);
  Serial.setTimeout(1);



  while (client.connected()) {
    if (Serial.available() > 0) {
      String s = Serial.readStringUntil('\n');
      Serial.println(s);
      client.println(s);
    }
    if (client.available() > 0) {
      Serial.print(client.readStringUntil('\r'));
    }
  }
}
