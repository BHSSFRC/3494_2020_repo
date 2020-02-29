package frc.robot;

//this class contains hardware constants such as motor ports
public class RobotMap {
    public static class DRIVETRAIN {
        public static final int LEFT_MASTER = 17;
        public static final int LEFT_SLAVE = 1;
        public static final int RIGHT_MASTER = 15;
        public static final int RIGHT_SLAVE = 14;
        public static final int TEMP_SOLENOID = 6;//random number

        public static final double MAX_TEMP = 60;
    }

    public static class SHOOTER {
        public static final int RIGHT = 12;
        public static final int LEFT = 13;
        public static final int HOOD_MAIN_UP = 4;
        public static final int HOOD_MAIN_DOWN = 5;
        public static final int HOOD_LIMIT_UP = 2;
        public static final int HOOD_LIMIT_DOWN = 3;
    }

    public static class INTAKE {
        public static final int MOTOR = 8;
        public static final int CYLINDER_OUT = 1;
        public static final int CYLINDER_IN = 0;
    }

    public static class HOPPER {
        public static final int MOTOR = 7;
    }

    public static class MAGAZINE {
        public static final int FRONT = 10;
        public static final int BOTTOM = 9;
        public static final int TOP = 4;
    }

    public static final class TURRET {
        public static final int MOTOR = 5;

        public static final int ENCODER_CHANNEL_A = 3;
        public static final int ENCODER_CHANNEL_B = 4;
        public static final int LIMIT_SWITCH_BACK = 0;
        public static final int LIMIT_SWITCH_FRONT = 1;

        public static final double RANGE_OF_MOTION = 260;
        public static final double ENCODER_COUNTS_PER_DEGREE = 10.65;
    }

    public static class OI {
        public static int PRIMARY_XBOX = 0;
        public static int SECONDARY_XBOX = 2;
        public static int BUTTON_BOARD = 3;

        //xbox bindings
        //public static int INTAKING_ROUTINE = 1;
        public static int RUN_SHOOTER = 6;
        public static int SHOOTER_FORWARD = 2;
        public static int SHOOTER_BACKWARD = 3;
        public static int SPIN_HOPPER_MAGAZINE = 5;

        //button board bindings
        public static int RUN_MAGAZINE = 7;
        public static int RUN_HOPPER = 8;

        public static int REVERSE_INTAKE = 4;

        public static int RELEASE_CLIMBER = 12;
        public static int DRIVE_CLIMBER = 14;
        public static int REVERSE_CLIMBER = 13;
        public static int SAFETY_CLIMBER  = 9;

        public static int QUICK_TURRET_LIMITS = 2;
        public static int TURRET_TO_START_POS = 11;
        public static int AIM_BOT = 10;

        public static int SHOOTER_LOW = 1;
        public static int SHOOTER_MED = 3;
        public static int SHOOTER_HIGH = 6;

        public static int TOGGLE_LED = 8;
    }

    public static class CLIMBER {
        public static int PANCAKE = 6;
        public static int WHEEL_OF_FORTUNE = 7;
        public static int MOTOR1 = 2;
        public static int MOTOR2 = 3;
        public static double CLIMB_UP_POWER = 0.2;
        public static double CLIMB_DOWN_POWER = -.7;
        public static double CLIMB_UP_RAMPRATE = 0.5;
    }

    public static class SENSORS {
        public static int IMU = 0;

        public static int LINEBREAK_BOT = 6;
        public static int LINEBREAK_TOP = 5;

        public static int SPIKE = 0;
    }
    
    public static class COMPRESSOR {
        public static int PCM1 = 1;
    }
}
