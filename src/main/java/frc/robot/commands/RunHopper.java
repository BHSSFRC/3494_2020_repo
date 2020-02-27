package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Hopper;


public class RunHopper extends CommandBase {

    public RunHopper() {
        addRequirements(Hopper.getInstance());
    }

    @Override
    public void execute() {
        Hopper.getInstance().spin(RobotConfig.MOTOR_SPEEDS.MAGAZINE);//should be given its own non hopper power
    }

    @Override
    public void end(boolean interrupted) {
        Hopper.getInstance().stop();
    }
}
