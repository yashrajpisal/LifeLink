// package com.kurukshetra.view;

// import javafx.util.Duration;

// public class AppSettings {

//     // Flip this to true (or wire it to a future Settings toggle) to shorten
//     // every animation in the app down to ~0ms for users who prefer reduced motion.
//     public static boolean reduceMotion = false;

//     public static Duration dur(double millis) {

//         if (reduceMotion) {

//             return Duration.millis(1);
//         }

//         return Duration.millis(millis);
//     }
// }



package com.kurukshetra.view.nurse;

import javafx.util.Duration;

public class NurseAppSettings {

    // Flip this to true (or wire it to a future Settings toggle) to shorten
    // every animation in the app down to ~0ms for users who prefer reduced motion.
    public static boolean reduceMotion = false;

    public static Duration dur(double millis) {

        if (reduceMotion) {

            return Duration.millis(1);
        }

        return Duration.millis(millis);
    }
}