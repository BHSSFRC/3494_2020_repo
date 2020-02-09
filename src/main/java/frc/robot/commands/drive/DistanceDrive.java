package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.DriveTrain;


public class DistanceDrive extends CommandBase {
    private double dist;
    private double distEncoderInitial;
    private double distEncoderGoal;
    private double distEncoderTraveled;
    private double speed;

    public DistanceDrive(double distance) {
        this.dist = distance;
        this.distEncoderInitial = DriveTrain.getInstance().getEncoderPosition();
        this.distEncoderGoal = this.dist * RobotConfig.DRIVE_STRAIGHT.ENCODER_TICKS_PER_INCH;
        this.speed = .2;
        SmartDashboard.putBoolean("DriveDistance?", true);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.distEncoderTraveled = Math.abs(DriveTrain.getInstance().getEncoderPosition() - this.distEncoderInitial);
        DriveTrain.getInstance().tankDrive(this.speed, this.speed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return (this.distEncoderTraveled > this.distEncoderGoal);
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrain.getInstance().stop();
        SmartDashboard.putBoolean("DriveStraight?", false);
    }
}
