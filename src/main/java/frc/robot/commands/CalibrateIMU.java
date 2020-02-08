package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotConfig;
import frc.robot.sensors.IMU;
import frc.robot.util.QuadTimer;

public class CalibrateIMU extends CommandBase {
    private QuadTimer timer;
    private double startDrift;

    public CalibrateIMU() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        this.timer = new QuadTimer();
    }

    @Override
    public void initialize() {
        this.timer.start();
        this.startDrift = this.timer.get();
        SmartDashboard.putNumber("Calibrate", 666);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        if (this.timer.get() > 60) {
            RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND = (IMU.getInstance().getYaw() - this.startDrift) / this.timer.get();
            SmartDashboard.putNumber("Calibrate", RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND);
            return true;
        }
        return false;
    }
}
