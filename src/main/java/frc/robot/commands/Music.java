package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Music extends CommandBase {

    public Music() {
        addRequirements(DriveTrain.getInstance());
    }

    @Override
    public void initialize() {
        DriveTrain.getInstance().beepBoop("mbk.chrp");
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
