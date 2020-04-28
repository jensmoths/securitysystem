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
  client.connect("192.168.1.46", 40000);
  client.print(ESP.getChipId());
  client.print(" ");
  client.print("kitchen");
  client.println(""); 





  while (true) {

    if (!client.connected()) {
      Serial.println("connection failed");
      delay(5000);
      return;
    }


    // This will send a string to the server
    Serial.println("sending data to server");
    if (client.connected()) {
      if (Serial.available() > 0) {
      String s = Serial.readString();
      client.println(s);
      Serial.println(s);
      }
    }

    delay(10000);
  }
}
