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
#define STATE_PIN 15

// SET_LOOP_TASK_STACK_SIZE(12*1024);

#define bleServerName "ESP32 LightHouse"
#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_STATE_UUID "93758842-6624-49aa-a286-abdfff1f4efa"
#define CHARACTERISTIC_CURRENT_COLOR_UUID "8c25e317-ac1a-47ee-bedc-53c6241487c2"
#define CHARACTERISTIC_BRIGHTNESS_UUID "69a5a500-de3a-4da4-a561-74579f1905ff"

BLECharacteristic rgbStripStateCharacteristics(CHARACTERISTIC_STATE_UUID, BLECharacteristic::PROPERTY_READ   |
                                                                          BLECharacteristic::PROPERTY_NOTIFY |
                                                                          BLECharacteristic::PROPERTY_WRITE_NR);
BLEDescriptor rgbStripStateDescriptor(BLEUUID((uint16_t)0x2903));
// BleMidiCharacteristicCallbacks *pBleMidiCallbacks = new BleMidiCharacteristicCallbacks();

BLECharacteristic rgbStripCurrentColorCharacteristics(CHARACTERISTIC_CURRENT_COLOR_UUID, BLECharacteristic::PROPERTY_READ   |
                                                                                          BLECharacteristic::PROPERTY_NOTIFY |
                                                                                          BLECharacteristic::PROPERTY_WRITE_NR);
BLEDescriptor rgbStripCurrentColorDescriptor(BLEUUID((uint16_t)0x2903));

BLECharacteristic rgbStripBrightnessCharacteristics(CHARACTERISTIC_BRIGHTNESS_UUID, BLECharacteristic::PROPERTY_READ   |
                                                                                          BLECharacteristic::PROPERTY_NOTIFY |
                                                                                          BLECharacteristic::PROPERTY_WRITE_NR);
BLEDescriptor rgbStripBrightnessDescriptor(BLEUUID((uint16_t)0x2903));

bool deviceConnected = false;
bool oldDeviceConnected = false;

bool loopStarted = false;

bool rgbStripState = false;
bool colorChanged = false;
int red_value = 255;
int green_value = 255;
int blue_value = 255;

class MyServerCallbacks: public BLEServerCallbacks {
  void onConnect(BLEServer* pServer) {
    deviceConnected = true;
  };
  void onDisconnect(BLEServer* pServer) {
    deviceConnected = false;
  }
};

class StateCharacteristicCallBack: public BLECharacteristicCallbacks
{
  //This method not called
  void onWrite(BLECharacteristic *characteristic_)
  {
    Serial.println("Write char data is received"); 
    String state = String(characteristic_->getValue().c_str());
    Serial.println(state);
    if(String(state).equals(String("on"))) {
      digitalWrite(STATE_PIN, HIGH);
    }else {
      digitalWrite(STATE_PIN, LOW);
    }
  }

  void onRead(BLECharacteristic *characteristic_)
  {
    Serial.println("Read characteristic data"); 
  }
};

class ColorCharacteristicCallBack: public BLECharacteristicCallbacks
{
  void onWrite(BLECharacteristic *characteristic_)
  {
    Serial.println("Write char data is received"); 
    int color = String(characteristic_->getValue().c_str()).toInt();
    Serial.println(color);

    red_value = (color >> 16) & 0xFF;
    green_value = (color >> 8) & 0xFF;
    blue_value = color & 0xFF;

    analogWrite(RED_PIN, red_value);
    analogWrite(GREEN_PIN, green_value);
    analogWrite(BLUE_PIN, blue_value);
  }

  void onRead(BLECharacteristic *characteristic_)
  {
    Serial.println("Read characteristic data"); 
  }
};

class BrightnessCharacteristicCallBack: public BLECharacteristicCallbacks
{
  void onWrite(BLECharacteristic *characteristic_)
  {
    Serial.println("Write char data is received"); 
    int brightness = String(characteristic_->getValue().c_str()).toInt();
    Serial.println(brightness);

    analogWrite(RED_PIN, red_value*brightness/100);
    analogWrite(GREEN_PIN, green_value*brightness/100);
    analogWrite(BLUE_PIN, blue_value*brightness/100);
  }

  void onRead(BLECharacteristic *characteristic_)
  {
    Serial.println("Read characteristic data"); 
  }
};

class DescrCallBack: public BLEDescriptorCallbacks
{
  void onWrite(BLEDescriptor *desc)
  {
    Serial.println("Descript write Data received!");
  }

  void onRead(BLEDescriptor *desc)
  {
    Serial.println("Descript read Data received!");
  }
};

void setup() {
  pinMode(RED_PIN, OUTPUT);
  pinMode(GREEN_PIN, OUTPUT);
  pinMode(BLUE_PIN, OUTPUT);
  pinMode(STATE_PIN, OUTPUT);

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
  rgbStripStateCharacteristics.setCallbacks(new StateCharacteristicCallBack());
  // bmeHumidityCharacteristics.addDescriptor(new BLE2902());
  rgbStripStateCharacteristics.addDescriptor(&rgbStripStateDescriptor);


  //COLOR
  pService->addCharacteristic(&rgbStripCurrentColorCharacteristics);
  rgbStripCurrentColorDescriptor.setValue("Current Color");
  rgbStripCurrentColorCharacteristics.setCallbacks(new ColorCharacteristicCallBack());
  // bmeHumidityCharacteristics.addDescriptor(new BLE2902());
  rgbStripCurrentColorCharacteristics.addDescriptor(&rgbStripCurrentColorDescriptor);

  //BRIGHTNESS
  pService->addCharacteristic(&rgbStripBrightnessCharacteristics);
  rgbStripBrightnessDescriptor.setValue("Current Brightness");
  rgbStripBrightnessCharacteristics.setCallbacks(new BrightnessCharacteristicCallBack());
  // bmeHumidityCharacteristics.addDescriptor(new BLE2902());
  rgbStripBrightnessCharacteristics.addDescriptor(&rgbStripBrightnessDescriptor);


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
    rgbStripStateCharacteristics.notify();

    rgbStripStateCharacteristics.setValue("off");
    rgbStripStateCharacteristics.notify();

    rgbStripCurrentColorCharacteristics.setValue("16711680");
    rgbStripCurrentColorCharacteristics.notify();

    rgbStripBrightnessCharacteristics.setValue("100");
    rgbStripBrightnessCharacteristics.notify();

    analogWrite(RED_PIN, red_value);
    analogWrite(GREEN_PIN, green_value);
    analogWrite(BLUE_PIN, blue_value);
    digitalWrite(STATE_PIN, HIGH);
    loopStarted = true;
  }

  if (deviceConnected) {

  }
  
  if (!deviceConnected && oldDeviceConnected) {
        delay(500); // give the bluetooth stack the chance to get things ready
        BLEDevice::startAdvertising(); // restart advertising
        Serial.println("start advertising");
        oldDeviceConnected = deviceConnected;
    }
    // connecting
    if (deviceConnected && !oldDeviceConnected) {
        // do stuff here on connecting
        oldDeviceConnected = deviceConnected;
    }

  delay(2000);
}