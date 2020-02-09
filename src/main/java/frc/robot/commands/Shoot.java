package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

    public Shoot() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Shooter.getInstance().shoot(OI.getINSTANCE().getXboxRightX() * SmartDashboard.getNumber("Shooter Max Power", 1));
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }
}
