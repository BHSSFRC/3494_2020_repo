package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.sensors.IMU;
import frc.robot.util.QuadTimer;

public class CalibrateIMU extends CommandBase {
    private QuadTimer timer;
    private double startDrift;
    private int secondsPassed;
    private boolean isPhaseTwo = false;
    private boolean isFinished = false;

    public CalibrateIMU() {
        this.timer = new QuadTimer();
    }

    @Override
    public void initialize() {
        this.timer.start();
        this.secondsPassed = 0;
        this.startDrift = this.timer.get();

        SmartDashboard.putNumber("Calibrate", 666);
    }

    @Override
    public void execute() {
        if (!this.isPhaseTwo && this.timer.get() > RobotConfig.SENSORS.IMU_CALIBRATION_TIME / 2) {
            RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_ONE = (IMU.getInstance().getYaw() - this.startDrift) / this.timer.delta();
            SmartDashboard.putNumber("Calibrate1", RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_ONE);
            IMU.getInstance().reset();
            this.startDrift = IMU.getInstance().getYaw();
            this.isPhaseTwo = true;
        }
        if (this.isPhaseTwo && this.timer.get() > RobotConfig.SENSORS.IMU_CALIBRATION_TIME){
            RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_TWO = (IMU.getInstance().getYaw() - this.startDrift) / (this.timer.delta());
            SmartDashboard.putNumber("Calibrate2", RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_TWO);
            this.isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }
}
