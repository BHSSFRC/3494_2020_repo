package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;


public class RunHMWhileShooting extends CommandBase {
    private double targetRPM;
    public RunHMWhileShooting(double targetRPM) {
        addRequirements(Hopper.getInstance(), Magazine.getInstance());
        this.targetRPM = targetRPM;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(!Robot.getLinebreakTop().lineBroken() || Shooter.getInstance().atTargetSpeed(this.targetRPM)){
            Hopper.getInstance().spin(RobotConfig.MAGAZINE.HOPPER_DEFAULT_POWER);
            Magazine.getInstance().run(true, true);
        }else{
            Hopper.getInstance().stop();
            Magazine.getInstance().stop();
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Hopper.getInstance().stop();
        Magazine.getInstance().stop();
    }
}
