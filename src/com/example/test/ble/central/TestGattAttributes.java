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

package com.example.test.ble.central;

import java.util.HashMap;

import com.example.test.ble.BleConstants;


/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class TestGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();

    static {
        // Sample Services.
        attributes.put(BleConstants.SERVICE_UUID, "E3 Gatt Service");
        // Sample Characteristics.
        attributes.put(BleConstants.REQUEST_DATA, "REQUEST_DATA");
        attributes.put(BleConstants.REQUEST_START, "REQUEST_START");
        attributes.put(BleConstants.REQUEST_END, "REQUEST_END");
        attributes.put(BleConstants.RESPONSE_DATA, "RESPONSE_DATA");
        attributes.put(BleConstants.RESPONSE_START, "RESPONSE_START");
        attributes.put(BleConstants.RESPONSE_INCREMENT, "RESPONSE_INCREMENT");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
