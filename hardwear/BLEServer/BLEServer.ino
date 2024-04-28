/*
    Based on Neil Kolban example for IDF: https://github.com/nkolban/esp32-snippets/blob/master/cpp_utils/tests/BLE%20Tests/SampleServer.cpp
    Ported to Arduino ESP32 by Evandro Copercini
    updates by chegewara
*/

#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>

// See the following for generating UUIDs:
// https://www.uuidgenerator.net/

#define RED_PIN 19
#define GREEN_PIN 21
#define BLUE_PIN 18

#define bleServerName "ESP32 LightHouse"
#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_STATE_UUID "93758842-6624-49aa-a286-abdfff1f4efa"
#define CHARACTERISTIC_CURRENT_COLOR_UUID "8c25e317-ac1a-47ee-bedc-53c6241487c2"

BLECharacteristic rgbStripStateCharacteristics(CHARACTERISTIC_STATE_UUID, BLECharacteristic::PROPERTY_NOTIFY);
BLEDescriptor rgbStripStateDescriptor(BLEUUID((uint16_t)0x2903));

BLECharacteristic rgbStripCurrentColorCharacteristics(CHARACTERISTIC_CURRENT_COLOR_UUID, BLECharacteristic::PROPERTY_NOTIFY);
BLEDescriptor rgbStripCurrentColorDescriptor(BLEUUID((uint16_t)0x2903));

bool deviceConnected = false;

bool loopStarted = false;

class MyServerCallbacks: public BLEServerCallbacks {
  void onConnect(BLEServer* pServer) {
    deviceConnected = true;
  };
  void onDisconnect(BLEServer* pServer) {
    deviceConnected = false;
  }
};

void setup() {
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);

  Serial.begin(115200);
  Serial.println("Starting BLE work!");

  BLEDevice::init(bleServerName);
  BLEServer *pServer = BLEDevice::createServer();
  pServer->setCallbacks(new MyServerCallbacks());
  BLEService *pService = pServer->createService(SERVICE_UUID);
  // pCharacteristic->setValue("Hello World says Neil");

  //STATE
  pService->addCharacteristic(&rgbStripStateCharacteristics);
  rgbStripStateDescriptor.setValue("Current State");
  // bmeHumidityCharacteristics.addDescriptor(new BLE2902());
  rgbStripStateCharacteristics.addDescriptor(&rgbStripStateDescriptor);

  //COLOR
  pService->addCharacteristic(&rgbStripCurrentColorCharacteristics);
  rgbStripCurrentColorDescriptor.setValue("Current Color");
  // bmeHumidityCharacteristics.addDescriptor(new BLE2902());
  rgbStripCurrentColorCharacteristics.addDescriptor(&rgbStripCurrentColorDescriptor);

  pService->start();
  // BLEAdvertising *pAdvertising = pServer->getAdvertising();  // this still is working for backward compatibility
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->addServiceUUID(SERVICE_UUID);
  pAdvertising->setScanResponse(true);
  pAdvertising->setMinPreferred(0x06);  // functions that help with iPhone connections issue
  pAdvertising->setMinPreferred(0x12);
  // pServer->getAdvertising()->start();
  BLEDevice::startAdvertising();
  Serial.println("Characteristic defined! Now you can read it in your phone!");
}

void loop() {
  // put your main code here, to run repeatedly:
  if(!loopStarted) {
    rgbStripStateCharacteristics.setValue("off");
    rgbStripStateCharacteristics.notify();

    rgbStripCurrentColorCharacteristics.setValue("225,0,0");
    rgbStripCurrentColorCharacteristics.notify();
    loopStarted = true;
  }

  if (deviceConnected) {
      
  }

  delay(2000);
}