package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DriveTrain extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private TalonSRX leftMaster;
    private TalonSRX leftSlave;
    private TalonSRX rightMaster;
    private TalonSRX rightSlave;

    /**
     * The Singleton instance of this DriveTrain. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static DriveTrain INSTANCE = new DriveTrain();

    /**
     * Creates a new instance of this DriveTrain.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private DriveTrain() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        this.leftMaster = new TalonSRX(RobotMap.DRIVETRAIN.LEFT_MASTER);
        this.leftSlave = new TalonSRX(RobotMap.DRIVETRAIN.LEFT_SLAVE);
        this.rightMaster = new TalonSRX(RobotMap.DRIVETRAIN.RIGHT_MASTER);
        this.rightSlave = new TalonSRX(RobotMap.DRIVETRAIN.RIGHT_SLAVE);

        this.leftSlave.follow(this.leftMaster);
        this.rightSlave.follow(this.rightMaster);
    }

    public void tankDrive(double leftPower, double rightPower){
        this.leftMaster.set(ControlMode.PercentOutput, leftPower);
        this.rightMaster.set(ControlMode.PercentOutput, rightPower);
    }

    /**
     * Returns the Singleton instance of this DriveTrain. This static method
     * should be used -- {@code DriveTrain.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static DriveTrain getInstance() {
        return INSTANCE;
    }

}

