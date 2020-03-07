package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.PreShooter;


public class ReverseIntake extends CommandBase {

    public ReverseIntake() {
        addRequirements(PreShooter.getInstance(),
                Intake.getInstance(),
                Hopper.getInstance(),
                Magazine.getInstance());
        System.out.println("Construct Reverse Intaking");
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Reverse Intake", true);
        System.out.println("Start Reverse Intaking");
    }

    @Override
    public void execute() {
        PreShooter.getInstance().spin(-RobotConfig.SHOOTER.PRESHOOTER_POWER);
        Magazine.getInstance().runReverse();
        Hopper.getInstance().spin(-RobotConfig.MAGAZINE.HOPPER_DEFAULT_POWER);
        Intake.getInstance().setDeployed(true);
        Intake.getInstance().runIntake(-.2);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Reverse Intake", false);
        System.out.println("Stop Reverse Intaking" + interrupted);
        PreShooter.getInstance().stop();
        Magazine.getInstance().stop();
        Hopper.getInstance().stop();
        Intake.getInstance().stop();
    }
}
