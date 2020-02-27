package frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Turret;

public class QuickTurretLimit extends CommandBase {

    public QuickTurretLimit() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Turret.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Turret.getInstance().spin(RobotConfig.TURRET.DEFAULT_SPIN_POWER);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return Turret.getInstance().atFrontLimit();
    }

    @Override
    public void end(boolean interrupted) {
        Turret.getInstance().spin(0);
        Turret.getInstance().resetEncoder();
        Turret.getInstance().setAllLimits();
        CommandScheduler.getInstance().schedule(new SpinToSetpoint(130));
    }
}