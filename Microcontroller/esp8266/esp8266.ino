#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

String locationString = "location";
int buttonState = 0;         // current state of the button
int lastButtonState = 0;     // previous state of the button


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

  pinMode(4, OUTPUT);
  pinMode(0, INPUT);
  pinMode(LED_BUILTIN, OUTPUT); 



}

void loop() {

  WiFiClient door;
  Serial.println("connecting");
  door.connect("192.168.31.181", 3000);
  door.print(ESP.getChipId());
  door.print(" ");
  door.print("kitchen");
  door.print(" ");
  door.print("door");
  door.println();

  delay(1000);

  WiFiClient proximity;
  Serial.println("connecting");
  proximity.connect("192.168.31.181", 3000);
  proximity.print(ESP.getChipId() + 1);
  proximity.print(" ");
  proximity.print("hallway");
  proximity.print(" ");
  proximity.print("proximity");
  proximity.println();

  //heartbeat
  while (proximity.connected()) {

    if (Serial.available() > 0) {
      String s = Serial.readString();
      Serial.println(s);
      if (s.equals("ledOn")) {
        digitalWrite(4, HIGH);
        digitalWrite(LED_BUILTIN, LOW); 
      } else if (s.equals("ledOff")) {
        digitalWrite(4, LOW);
        digitalWrite(LED_BUILTIN, HIGH);
      }
    }



    if (door.available() > 0) {
      String s2 = door.readString();
      Serial.println("Server: " + s2);
      if (s2.equals("ledOn")) {
        digitalWrite(4, HIGH);
        digitalWrite(LED_BUILTIN, LOW); 
      } else if (s2.equals("ledOff")) {
        digitalWrite(4, LOW);
        digitalWrite(LED_BUILTIN, HIGH);
      }
    }



    //magnet.println("heartbeat");
    //delay(2000);

    buttonState = digitalRead(0);

    if (buttonState != lastButtonState) {
      if (buttonState == LOW) {
        proximity.println("ledOn");
        Serial.println("button on");
      } else {
        Serial.println("button off");
      }
    }
    lastButtonState = buttonState;
  }
}
