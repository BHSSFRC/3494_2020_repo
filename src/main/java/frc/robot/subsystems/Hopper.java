package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hopper extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private TalonSRX motor;

    /**
     * The Singleton instance of this Hopper. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static Hopper INSTANCE = new Hopper();

    /**
     * Creates a new instance of this Hopper.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private Hopper() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        this.motor = new TalonSRX(RobotMap.HOPPER.MOTOR);
        this.motor.configOpenloopRamp(1);
    }

    public void spin(double power){
        this.motor.set(ControlMode.PercentOutput, power);
    }

    public void stop(){
        this.motor.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Returns the Singleton instance of this Hopper. This static method
     * should be used -- {@code Hopper.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static Hopper getInstance() {
        return INSTANCE;
    }

}

