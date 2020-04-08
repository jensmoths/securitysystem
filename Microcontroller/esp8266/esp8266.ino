#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager



void setupWifiManager(){
WiFiManager wifiManager;
  
  //uncomment to reset saved settings
  //wifiManager.resetSettings();

  //fetches ssid and pass from eeprom and tries to connect
  //if it does not connect it starts an access point with the specified name
  //here  "AutoConnectAP"
  //and goes into a blocking loop awaiting configuration
  wifiManager.autoConnect("ConnectToSetupSecuritySystem");
  
  //if you get here you have connected to the WiFi
  Serial.println("connected to wifi");
  
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

}
