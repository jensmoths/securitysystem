#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino

//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

//String locationString = "location";
int lastDistance = 0;         // current state of the distance

const String IP = "83.254.129.68";
const int PORT = 40000;
const String TYPE = "proximity";
// defines pins numbers
const int trigPin = 0;
const int echoPin = 2;
// defines variables
long duration;
int distance; // previous state of the distance
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

void connectToServer(String location) {
  while (true) {
    Serial.println("connecting to server");
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
  Serial.println("connected to server");
}

void reconnectToServer() {
  while (true) {
    Serial.println("reconnecting to server");
    client.connect(IP, PORT);
    client.print(ESP.getChipId());
    client.print("|");
    client.println(TYPE);

    if (client.connected()) break;
    delay(10000);
  }
  Serial.println("connected to server");
}




void setup() {
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input

  //start serial for debugging
  Serial.begin(115200);

  //method for easy connection to a wifi
  String location = setupWifiManager();

  connectToServer(location);
}

void loop() {
  while (client.connected()) {
    // Clears the trigPin
    digitalWrite(trigPin, LOW);
    delayMicroseconds(2);

    // Sets the trigPin on HIGH state for 10 micro seconds
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);

    // Reads the echoPin, returns the sound wave travel time in microseconds
    duration = pulseIn(echoPin, HIGH);

    // Calculating the distance
    distance = duration * 0.034 / 2;

    // Prints the distance on the Serial Monitor
    delay(500);
     if ((lastDistance - distance) > 3 || (lastDistance - distance) <  -3  ) {
    if (distance < 15) {
      client.println("Wakanda forever ");
      Serial.print("Distance: ");
      Serial.println(distance);

    
    }
    lastDistance = distance;
     }



    //    if (client.available() > 0) {
    //      String message = client.readString();
    //      Serial.println("Server: " + message);
    //      if (message.equals("ledOn")) {
    //        digitalWrite(4, HIGH);
    //        digitalWrite(LED_BUILTIN, LOW);
    //      } else if (message.equals("ledOff")) {
    //        digitalWrite(4, LOW);
    //        digitalWrite(LED_BUILTIN, HIGH);
    //      }
    //   }
  }
  reconnectToServer();
}