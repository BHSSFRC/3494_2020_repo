package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;

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

    //should turn between -180 and 180 degrees, no more
    public TurnDegrees(double degrees) {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(DriveTrain.getInstance());
        this.turnDegrees = degrees;
    }

    @Override
    public void initialize() {
        IMU.getInstance().reset();
        //IMU should reset yaw to 0, now set initial yaw to 180
        this.initialYaw = IMU.getInstance().getYaw() + 180;
        this.setpoint = this.initialYaw + this.turnDegrees;
    }

    @Override
    public void execute() {
        this.currentDegrees = IMU.getInstance().getYaw() + 180;
        double delta = this.currentDegrees - this.setpoint;
        SmartDashboard.putNumber("DriveTurn Offset", delta);

        //should be between -1 and 1
        double output = delta / 180;

        output *= RobotConfig.DRIVE_STRAIGHT.TURN_SPEED;
        SmartDashboard.putNumber("Turn Power", output);
        DriveTrain.getInstance().tankDrive(output, -output);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return Math.abs(this.currentDegrees - this.setpoint) < 5;
    }
}
