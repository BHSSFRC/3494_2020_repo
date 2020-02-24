package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotConfig;
import frc.robot.RobotMap;

import java.awt.*;

public class Magazine extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.
    private TalonSRX front;
    private TalonSRX bottom;
    private TalonSRX top;

    /**
     * TODO: create teleop routine
     * 1 Intake balls: Hopper on, front on, bottom and top off
     * 2 When Sensor 1 sees balls: Hopper on, front and bottom on, top off
     * 3 When Sensor 2 sees balls: Hopper on, front on, bottom and top off
     * 4 Once Sensor 1 sees balls, Magazine is full: Hopper and magazine motors off
     */

    /**
     * The Singleton instance of this Magazine. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static Magazine INSTANCE = new Magazine();

    /**
     * Creates a new instance of this Magazine.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private Magazine() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        this.front = new TalonSRX(RobotMap.MAGAZINE.FRONT);
        this.bottom = new TalonSRX(RobotMap.MAGAZINE.BOTTOM);
        this.bottom.setInverted(true);
        this.top = new TalonSRX(RobotMap.MAGAZINE.TOP);
    }

    public void runFront(boolean on){
        if(on){
            this.front.set(ControlMode.PercentOutput, RobotConfig.MAGAZINE.MOTOR_DEFAULT_POWER);
        }else{
            this.front.set(ControlMode.PercentOutput, 0);
        }
    }

    public void runBottom(boolean on){
        if(on){
            this.bottom.set(ControlMode.PercentOutput, RobotConfig.MAGAZINE.MOTOR_DEFAULT_POWER);
        }else{
            this.bottom.set(ControlMode.PercentOutput, 0);
        }
    }

    public void runTop(boolean on){
        if(on){
            this.top.set(ControlMode.PercentOutput, 0.1);
        }else{
            this.top.set(ControlMode.PercentOutput, 0);
        }
    }

    public void stop(){
        this.runFront(false);
        this.runBottom(false);
        this.runTop(false);
    }

    /**
     * Returns the Singleton instance of this Magazine. This static method
     * should be used -- {@code Magazine.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static Magazine getInstance() {
        return INSTANCE;
    }

}

