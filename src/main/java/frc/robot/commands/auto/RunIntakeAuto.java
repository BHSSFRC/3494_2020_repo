package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Intake;


public class RunIntakeAuto extends CommandBase {

    public RunIntakeAuto() {
        addRequirements(Intake.getInstance());
    }

    @Override
    public void initialize() {
        Intake.getInstance().setDeployed(true);
    }

    @Override
    public void execute() {
        Intake.getInstance().runIntake((RobotConfig.MAGAZINE.INTAKE_DEFAULT_POWER));
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Intake.getInstance().stop();
        Intake.getInstance().setDeployed(false);
    }
}
