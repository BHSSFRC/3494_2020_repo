/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

//this class store software variables such as PID constants that change from time to time
public final class RobotConfig
{
    public static class DRIVE {
        public static final double POWER_CURVE_EXPONENT = 2.8;
    }

    public static class DRIVE_STRAIGHT {
        public static final int kP = 1;
        public static final int kI = 0;
        public static final int kD = 0;

        public static final double kP_DUMB = 5 / 360;

        public static final double TURN_SPEED = 1.0;
        public static final double ENCODER_TICKS_PER_INCH = 793.7;

        public static final double AUTO_LINE_INCHES = 40;
    }
    public static class SENSORS {
        public static double IMU_OFFSET_PER_SECOND_PHASE_ONE = -0.794775;
        public static double IMU_OFFSET_PER_SECOND_PHASE_TWO = 0.985840;
        public static double IMU_CALIBRATION_TIME = 60;
    }

    public static class MAGAZINE {
        public static double MOTOR_DEFAULT_POWER = 0.3;
        public static double TIME_AFTER_2ND_LINEBREAK_SENSOR = 1.0;
    }
}