package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Turret;


public class SpinTurret extends CommandBase {

    public SpinTurret() {
        addRequirements(Turret.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double power = OI.getINSTANCE().getXboxRightX() * 0.2;
        Turret.getInstance().spin(power);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Turret.getInstance().spin(0);
    }
}