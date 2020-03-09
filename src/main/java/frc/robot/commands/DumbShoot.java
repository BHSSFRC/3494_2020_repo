package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;


public class DumbShoot extends CommandBase {

    public DumbShoot() {
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Shooter.getInstance().shoot(.5);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().stop();
    }
}
