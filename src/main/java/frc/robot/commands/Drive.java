package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;


public class Drive extends CommandBase {

    public Drive() {
        addRequirements(DriveTrain.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double leftPower = OI.getINSTANCE().getLeftY();
        double rightPower = OI.getINSTANCE().getRightY();
        DriveTrain.getInstance().tankDrive(leftPower, rightPower);
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
