package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;

public class DriveTrain extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private TalonFX leftMaster;
    private TalonFX leftSlave;
    private TalonFX rightMaster;
    private TalonFX rightSlave;

    //private TalonSRX shooter;

    //private Solenoid tempControl;

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
        this.leftMaster = new TalonFX(RobotMap.DRIVETRAIN.LEFT_MASTER);
        this.leftMaster.setNeutralMode(NeutralMode.Brake);
        this.leftSlave = new TalonFX(RobotMap.DRIVETRAIN.LEFT_SLAVE);
        this.leftSlave.setNeutralMode(NeutralMode.Brake);
        this.leftMaster.setInverted(true);
        this.leftSlave.setInverted(true);
        this.rightMaster = new TalonFX(RobotMap.DRIVETRAIN.RIGHT_MASTER);
        this.rightMaster.setNeutralMode(NeutralMode.Brake);
        this.rightSlave = new TalonFX(RobotMap.DRIVETRAIN.RIGHT_SLAVE);
        this.rightSlave.setNeutralMode(NeutralMode.Brake);

        this.leftSlave.follow(this.leftMaster);
        this.rightSlave.follow(this.rightMaster);

        //this.shooter = new TalonSRX(RobotMap.DRIVETRAIN.SHOOTER);
        //setDefaultCommand(new Drive());

        //this.tempControl = new Solenoid(RobotMap.DRIVETRAIN.TEMP_SOLENOID);
    }

    public void tankDrive(double leftPower, double rightPower){
        if( SmartDashboard.getNumber("Left Y", OI.getINSTANCE().getLeftY()) != 0){

        }
        this.leftMaster.set(ControlMode.PercentOutput, leftPower);
        this.rightMaster.set(ControlMode.PercentOutput, rightPower);
    }

    public void initDefaultCommand(){
        setDefaultCommand(new Drive());
    }

    public boolean aboveMaxTemp(){
        if(this.rightMaster.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP ||
            this.rightSlave.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP ||
            this.leftMaster.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP ||
            this.leftSlave.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP) {
            return true;
        }else{
            return false;
        }
    }

    public double getLeftEncoderPosition(){
        return (this.leftMaster.getSelectedSensorPosition() + this.leftSlave.getSelectedSensorPosition()) / 2;
    }

    public double getRightEncoderPosition(){
        return (this.rightMaster.getSelectedSensorPosition() + this.rightSlave.getSelectedSensorPosition()) / 2;
    }

    public double getEncoderPosition(){
        return (this.getLeftEncoderPosition() + this.getRightEncoderPosition()) / 2;
    }

    public void openTempSolenoid(){
        //this.tempControl.set(true);
    }

    public void stop(){
        this.tankDrive(0,0);
    }

    /**public double getLeftMasterPosition(){
        return this.leftMaster.getSelectedSensorPosition();
    }

    public double getRightMasterPosition(){
        return this.rightMaster.getSelectedSensorPosition();
    }*/

    /**
     * Returns the Singleton instance of this DriveTrain. This static method
     * should be used -- {@code DriveTrain.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static DriveTrain getInstance() {
        return INSTANCE;
    }

}

