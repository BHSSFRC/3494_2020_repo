package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;


public class DistanceDrive extends CommandBase {
    private double dist;
    private double distEncoderGoal;
    private double distEncoderTraveled;
    private double speed;

    public DistanceDrive(double distance) {
        this.dist = distance;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        DriveTrain.getInstance().tankDrive(this.speed, this.speed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return (this.distEncoderTraveled > this.distEncoderGoal);
    }

    @Override
    public void end(boolean interrupted) {
    }
}
