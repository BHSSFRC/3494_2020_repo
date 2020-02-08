package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class DistanceDrive extends CommandBase {
    private double dist;

    public DistanceDrive(double distance) {
        this.dist = distance;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
