package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class WheelOfFortune extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private TalonSRX wheel;

    /**
     * The Singleton instance of this WheelOfFortune. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static WheelOfFortune INSTANCE = new WheelOfFortune();

    /**
     * Creates a new instance of this WheelOfFortune.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private WheelOfFortune() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        this.wheel = new TalonSRX(RobotMap.CONTROL_PANEL.WHEEL);
    }

    public void spin(double power){
        this.wheel.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        this.spin(0);
    }

    /**
     * Returns the Singleton instance of this WheelOfFortune. This static method
     * should be used -- {@code WheelOfFortune.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static WheelOfFortune getInstance() {
        return INSTANCE;
    }

}