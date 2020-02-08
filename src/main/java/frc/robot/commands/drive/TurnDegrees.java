package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.sensors.IMU;

public class TurnDegrees extends CommandBase {
    private double setpoint;
    private double initialYaw;
    private double turnDegrees;
    private double currentDegrees;

    //default constructor turns 0 degrees
    public TurnDegrees() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(DriveTrain.getInstance());
        this.turnDegrees = 90.0;
    }

    public TurnDegrees(double degrees) {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(DriveTrain.getInstance());
        this.turnDegrees = degrees;
    }

    @Override
    protected void initialize() {
        this.initialYaw = IMU.getInstance().getYaw();
        this.setpoint = this.initialYaw + this.turnDegrees;
        if(this.setpoint > 360){
            this.setpoint %= 360;
        }
        if(this.setpoint < 0){
            this.setpoint += 360;
        }
    }

    @Override
    protected void execute() {
        this.currentDegrees = IMU.getInstance().getYaw();
        double delta = this.currentDegrees - this.setpoint;
        SmartDashboard.putNumber("DriveTurn Offset", delta);
        if(delta > 180){
            delta = (360 - delta) * -1;
        }

        //should be between -1 and 1
        double output = delta / 180;

        output *= RobotConfig.DRIVE_STRAIGHT.TURN_SPEED;
        SmartDashboard.putNumber("Turn Power", output);
        DriveTrain.getInstance().tankDrive(output, -output);
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return Math.abs(this.currentDegrees - this.setpoint) < 5;
    }

    @Override
    protected void end() {
        DriveTrain.getInstance().stop();
    }

    @Override
    protected void interrupted() {

    }
}
