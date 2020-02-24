package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Hopper;


public class RunHopper extends CommandBase {

    public RunHopper() {
        addRequirements(Hopper.getInstance());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //Hopper.getInstance().spin(0.3);
        Hopper.getInstance().spin(RobotConfig.MAGAZINE.MOTOR_DEFAULT_POWER);//should be given its own non hopper power
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Hopper.getInstance().stop();
    }
}