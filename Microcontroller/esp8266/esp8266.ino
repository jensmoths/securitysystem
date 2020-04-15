#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

String locationString = "location";


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

  WiFiClient magnet;
  Serial.println("connecting");
  magnet.connect("192.168.31.181", 3000);
  magnet.print(ESP.getChipId());
  magnet.print(" ");
  magnet.print("kitchen");
  magnet.print(" ");
  magnet.print("magnet");
  magnet.println();
  delay(1000);

  //heartbeat
  while (magnet.connected()) {

    if (Serial.available() > 0) {
      String s = Serial.readString();
      //Serial.print(s);
      magnet.print(s);
    }
    if (magnet.available() > 0) {
      
      Serial.print(magnet.readStringUntil('\r'));
    }

    //magnet.println("heartbeat");
    //delay(2000);
  }




  //  while (siren.connected()) {
  //    if (Serial.available() > 0) {
  //      String s = Serial.readString();
  //      Serial.print(s);
  //      magnet.print(s);
  //    }
  //    if (magnet.available() > 0) {
  //      Serial.print("magnet: ");
  //      Serial.print(magnet.readStringUntil('\r'));
  //    }
  //    if (siren.available() > 0) {
  //      Serial.print("siren: ");
  //      Serial.print(siren.readStringUntil('\r'));
  //
  //    }
  //  }
}
