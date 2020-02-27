package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
>>>>>>> develop
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
<<<<<<< HEAD
import frc.robot.commands.drive.Drive;
=======
>>>>>>> develop

public class DriveTrain extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    private TalonFX leftMaster;
    private TalonFX leftSlave;
    private TalonFX rightMaster;
    private TalonFX rightSlave;

    private final static DriveTrain INSTANCE = new DriveTrain();

    private DriveTrain() {
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
    }

    public void tankDrive(double leftPower, double rightPower){
        this.leftMaster.set(ControlMode.PercentOutput, leftPower);
        this.rightMaster.set(ControlMode.PercentOutput, rightPower);
    }

    /**public boolean aboveMaxTemp(){
        if (this.rightMaster.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP) {
            return true;
        } else if (this.rightSlave.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP || this.leftMaster.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP || this.leftSlave.getTemperature() > RobotMap.DRIVETRAIN.MAX_TEMP) {
            return true;
        } else {
            return false;
        }
    }*/

    public double getLeftEncoderPosition(){
        return (this.leftMaster.getSelectedSensorPosition() + this.leftSlave.getSelectedSensorPosition()) / 2;
    }

    public double getRightEncoderPosition(){
        return (this.rightMaster.getSelectedSensorPosition() + this.rightSlave.getSelectedSensorPosition()) / 2;
    }

    public double getEncoderPosition(){
        return (this.getLeftEncoderPosition() + this.getRightEncoderPosition()) / 2;
    }

    public void stop(){
        this.tankDrive(0,0);
    }

    public static DriveTrain getInstance() {
        return INSTANCE;
    }

}

