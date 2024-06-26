package com.example.diplomenproekt.bluetooth;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String LIGHTSOURCE_STATE = "93758842-6624-49aa-a286-abdfff1f4efa";
    public static String LIGHTSOURCE_COLOR = "8c25e317-ac1a-47ee-bedc-53c6241487c2";
    public static String LIGHTSOURCE_BRIGHTNESS = "69a5a500-de3a-4da4-a561-74579f1905ff";

    public static String HEART_RATE_MEASUREMENT = "00002902-0000-1000-8000-00805f9b34fb";

    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put(LIGHTSOURCE_STATE, "On/Off");
        attributes.put(LIGHTSOURCE_COLOR, "Color");
        attributes.put(LIGHTSOURCE_BRIGHTNESS, "Brightness");


        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
