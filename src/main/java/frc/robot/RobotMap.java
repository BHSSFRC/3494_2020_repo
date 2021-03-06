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

    public static class OI {
        public static int LEFT_FLIGHT = 0;
        public static int RIGHT_FLIGHT = 1;
        public static int XBOX = 2;

        public static int DRIVE_TURN = 1;
        public static int DRIVE_STRAIGHT = 4;
        public static int DRIVE_DISTANCE = 3;

        public static int RUN_MAGAZINE = 2;
        public static int RUN_SHOOTER = 9;
    }

    public static class CLIMBER {
        public static int PANCAKE = 6;
        public static int WHEEL_OF_FORTUNE = 7;
        public static int MOTOR1 = 2;
        public static int MOTOR2 = 3;
        public static int RELEASE_BUTTON = 3;
        public static int DRIVE_BUTTON = 1;
        public static int REVERSE_BUTTON = 4;
        public static double CLIMB_UP_POWER = 1;
        public static double CLIMB_UP_RAMPRATE = 0.5;
    }

    public static class SENSORS {
        public static int IMU = 0;

        public static int LINEBREAK_BOT = 6;
        public static int LINEBREAK_TOP = 5;
    }
    
    public static class COMPRESSOR {
        public static int PCM1 = 1;
    }
}
