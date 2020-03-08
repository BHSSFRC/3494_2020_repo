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
        addRequirements(DriveTrain.getInstance());
        this.dist = distance;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("DriveDistance?", true);
        this.distEncoderInitial = DriveTrain.getInstance().getEncoderPosition();
        this.distEncoderGoal = Math.abs(this.dist) * RobotConfig.DRIVE_STRAIGHT.ENCODER_TICKS_PER_INCH;
        if(this.dist > 0){
            this.speed = 0.5;
        }else{
            this.speed = -.5;
        }
        this.distEncoderTraveled = 0;
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
        if(interrupted){
            System.out.println("Distance Drive interrupted");
        }
        SmartDashboard.putBoolean("DriveStraight?", false);
        DriveTrain.getInstance().stop();
    }
}
