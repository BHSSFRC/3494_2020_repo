package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;

    private OI(){
        leftFlight = new Joystick(RobotMap.OI.LEFT_FLIGHT);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_FLIGHT);
    }

    public double getLeftY(){
        return leftFlight.getY(GenericHID.Hand.kLeft);
    }

    public double getRightY(){
        return rightFlight.getY(GenericHID.Hand.kRight);
    }

    public static OI getINSTANCE(){
        return INSTANCE;
    }
}
