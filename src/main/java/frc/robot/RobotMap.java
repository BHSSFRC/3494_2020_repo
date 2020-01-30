package frc.robot;

//this class contains hardware constants such as motor ports
public class RobotMap {
    public static class DRIVETRAIN {
        public static final int LEFT_MASTER = 17;
        public static final int LEFT_SLAVE = 1;
        public static final int RIGHT_MASTER = 15;
        public static final int RIGHT_SLAVE = 14;
        public static final int TEMP_SOLENOID = 6;//random number

        public static final int SHOOTER = 8;
        public static final double MAX_TEMP = 60;
    }

    public static class SHOOTER {
        public static final int TOP = 12;
        public static final int BOT = 13;
    }

    public static class OI {
        public static int LEFT_FLIGHT = 0;
        public static int RIGHT_FLIGHT = 1;
        public static int XBOX = 2;
    }
}
