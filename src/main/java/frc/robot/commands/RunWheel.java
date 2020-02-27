package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.WheelOfFortune;


public class RunWheel extends CommandBase {
    private double power;

    public RunWheel(boolean right) {
        addRequirements(WheelOfFortune.getInstance());
        if (right) {
            power = RobotConfig.MOTOR_SPEEDS.WHEEL_OF_FORTUNE;
        }else {
            power = -RobotConfig.MOTOR_SPEEDS.WHEEL_OF_FORTUNE;
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        WheelOfFortune.getInstance().spin(this.power);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        WheelOfFortune.getInstance().stop();
    }
}
