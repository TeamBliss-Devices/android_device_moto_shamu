/*
 * Copyright (C) 2014 SlimRoms Project
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

package com.slim.device;

import com.slim.device.util.FileUtils;

import java.io.File;

/*
 * Very ugly class which enables or disables for now
 * all gesture controls on kernel level.
 * We need to do it this way for now to do not break 3rd party kernel.
 * Kernel should have a better per gesture control but as long
 * this is not changed by the manufacture we would break gesture control on every
 * 3rd party kernel. Hence we do it this way for now.
 */

public final class KernelControl {

    private static String CONFIG_PATH_WG = "/sys/android_touch/wake_gestures";
	private static String CONFIG_PATH_DT = "/sys/android_touch/doubletap2wake";
    private static String DOUBLETAP      = CONFIG_PATH_DT + "double_tap_enable";
    private static String SWEEP_UP       = CONFIG_PATH_WG + "sweep_up";
    private static String SWEEP_DOWN     = CONFIG_PATH_WG + "sweep_down";
    private static String SWEEP_LEFT     = CONFIG_PATH_WG + "sweep_left";
    private static String SWEEP_RIGHT    = CONFIG_PATH_WG + "sweep_right";

    private static String[] GESTURE_CONTROL_NODES = {
            DOUBLETAP,
            SWEEP_UP ,
            SWEEP_DOWN  ,
            SWEEP_LEFT ,
            SWEEP_RIGHT };

    private KernelControl() {
        // this class is not supposed to be instantiated
    }

    /**
     * Enable or disable gesture control.
     */
    public static void enableGestures(boolean enable) {
        for (int i = 0; i < GESTURE_CONTROL_NODES.length; i++) {
            if (new File(GESTURE_CONTROL_NODES[i]).exists()) {
                FileUtils.writeLine(GESTURE_CONTROL_NODES[i], enable ? "1" : "0");
            }
        }
    }

    /**
     * Check if kernel supports up gesture - slim specific.
     * As well ugly but needed for now.
     */
    public static boolean isUpSupported() {
        return new File(SWEEP_UP).exists();
    }

    /**
     * Do we have touch control at all?
     */
    public static boolean hasTouchscreenGestures() {
        return new File(DOUBLETAP).exists()
                && new File(SWEEP_DOWN).exists()
                && new File(SWEEP_LEFT).exists()
                && new File(SWEEP_RIGHT).exists();
    }

}
