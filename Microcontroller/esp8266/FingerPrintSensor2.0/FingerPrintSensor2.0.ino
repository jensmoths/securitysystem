#include <ESP8266WiFi.h>          //https://github.com/esp8266/Arduino
#include <Adafruit_Fingerprint.h>
#include <SoftwareSerial.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>


//needed libraries for WifiManager
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>         //https://github.com/tzapu/WiFiManager

//SoftSerial use pins D6 (TX) and D5 (RX).
SoftwareSerial swSer(5, 4);

//0x31
#define OLED_RESET 0
Adafruit_SSD1306 display(OLED_RESET);
Adafruit_Fingerprint finger = Adafruit_Fingerprint(&swSer);
WiFiClient client;

const String IP = "192.168.1.42";
const int PORT = 40000;
const String TYPE = "fingerprint";
int statusState = LOW;
int wifiReset = 16;
int wifiButton;
int resetState = 12;
unsigned long preMillis = 0;
unsigned long ms;
unsigned long beatMs;
unsigned long preBeat = 0;
unsigned long connectMs;
unsigned long preConnect = 0;
unsigned long reconnectMs;
unsigned long preReconnect = 0;

WiFiManager wifiManager;
// reconnecting, 10x10px
const unsigned char reconnectingBitmap [] PROGMEM = {
  0x08, 0x00, 0x1c, 0x00, 0x0b, 0x00, 0x01, 0x00, 0x40, 0x80, 0x40, 0x80, 0x20, 0x00, 0x34, 0x00,
  0x0e, 0x00, 0x04, 0x00
};
// connected, 10x10px
const unsigned char connectedBitmap [] PROGMEM = {
  0x10, 0x00, 0x38, 0x00, 0x10, 0x00, 0x10, 0x00, 0x12, 0x00, 0x12, 0x00, 0x02, 0x00, 0x02, 0x00,
  0x07, 0x00, 0x02, 0x00
};











String setupWifiManager() {


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

void setupFingerPrint() {
  // set the data rate for the sensor serial port
  finger.begin(57600);

  if (finger.verifyPassword()) {
    Serial.println("Found fingerprint sensor!");
  } else {
    Serial.println("Did not find fingerprint sensor :(");
    while (1) {
      delay(1);
    }
  }
  finger.getTemplateCount();
  Serial.print("Sensor contains "); Serial.print(finger.templateCount); Serial.println(" templates");
  Serial.println("Waiting for valid finger...");
}

void setupDisplay() {
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  display.setTextSize(1);
  display.setCursor(0, 0);
  display.setTextColor(WHITE);
  display.clearDisplay();
  display.display();
}
void resetWifi() {
  wifiButton = digitalRead(wifiReset);
  if (wifiButton == HIGH) {
    wifiManager.resetSettings();
    delay(2000);
    pinMode(resetState, OUTPUT);
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

void statusBlink() {
  ms = millis();
  if ((ms - preMillis) >= 1000 ) {
    preMillis = ms;
    if (statusState == LOW) {
      display.drawBitmap(0, 0,  reconnectingBitmap, 10, 10, WHITE);
      display.display();
      statusState = HIGH;
    } else {
      statusState = LOW;
      display.clearDisplay();
      display.display();
    }
  }
}


//add animation
void connectToServer(String location) {
  display.clearDisplay();
  display.display();
  resetWifi();
  while (true) {
    statusBlink();
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
  display.clearDisplay();
  display.drawBitmap(0, 0,  connectedBitmap, 10, 10, WHITE);
  display.display();
}

void reconnectToServer() {
  display.clearDisplay();
  display.display();
  resetWifi();
  while (true) {
    statusBlink();
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
  }
  display.clearDisplay();
  display.drawBitmap(0, 0,  connectedBitmap, 10, 10, WHITE);
  display.display();
}




void setup() {
  //setupDisplay();
  pinMode(wifiReset, INPUT);
  pinMode(resetState, INPUT);
  digitalWrite(resetState, LOW);
  Serial.begin(9600);
  client.setTimeout(250);

  setupFingerPrint();

  //method for easy connection to a wifi
  String location = setupWifiManager();

  connectToServer(location);
}

void loop() {
  resetWifi();
  if (client.connected()) {
    heartBeat();
    int fingerid = getFingerprintIDez();
    if (fingerid >= 0) {
      client.println("on");
      Serial.println(fingerid);
    }

    //Serial.println(getFingerprintIDez());
    delay(50);
    if (client.available() > 0) {
      char message = client.read();
      Serial.println(message);
      if (message == 'a') {
        int id = client.read();
        Serial.println(id);
        while (!getFingerprintEnroll(id));
      } else if (message == 'e') {
        finger.emptyDatabase();
        Serial.println("Database cleared");
      } else if (message == 'd') {
        int id = client.read();
        Serial.println(id);
        deleteFingerprint(id);
      }
    }

  } else reconnectToServer();
}

uint8_t getFingerprintEnroll(int id) {

  int p = -1;
  Serial.print("Waiting for valid finger to enroll as #"); Serial.println(id);
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
      case FINGERPRINT_OK:
        Serial.println("Image taken");
        break;
      case FINGERPRINT_NOFINGER:
        Serial.println(".");
        break;
      case FINGERPRINT_PACKETRECIEVEERR:
        Serial.println("Communication error");
        break;
      case FINGERPRINT_IMAGEFAIL:
        Serial.println("Imaging error");
        break;
      default:
        Serial.println("Unknown error");
        break;
    }
  }

  // OK success!

  p = finger.image2Tz(1);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  Serial.println("Remove finger");
  delay(2000);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
  }
  Serial.print("ID "); Serial.println(id);
  p = -1;
  Serial.println("Place same finger again");
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
      case FINGERPRINT_OK:
        Serial.println("Image taken");
        break;
      case FINGERPRINT_NOFINGER:
        Serial.print(".");
        break;
      case FINGERPRINT_PACKETRECIEVEERR:
        Serial.println("Communication error");
        break;
      case FINGERPRINT_IMAGEFAIL:
        Serial.println("Imaging error");
        break;
      default:
        Serial.println("Unknown error");
        break;
    }
  }

  // OK success!

  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK converted!
  Serial.print("Creating model for #");  Serial.println(id);

  p = finger.createModel();
  if (p == FINGERPRINT_OK) {
    Serial.println("Prints matched!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_ENROLLMISMATCH) {
    Serial.println("Fingerprints did not match");
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }

  Serial.print("ID "); Serial.println(id);
  p = finger.storeModel(id);
  if (p == FINGERPRINT_OK) {
    Serial.println("Stored!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Could not store in that location");
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error writing to flash");
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }
}

uint8_t deleteFingerprint(uint8_t id) {
  uint8_t p = -1;

  p = finger.deleteModel(id);

  if (p == FINGERPRINT_OK) {
    Serial.println("Deleted!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Could not delete in that location");
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error writing to flash");
    return p;
  } else {
    Serial.print("Unknown error: 0x"); Serial.println(p, HEX);
    return p;
  }
}


// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -1;

  // found a match!
  Serial.print("Found ID #"); Serial.print(finger.fingerID);
  Serial.print(" with confidence of "); Serial.println(finger.confidence);
  return finger.fingerID;
}