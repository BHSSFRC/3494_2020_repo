package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.RobotConfig;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;


public class Drive extends CommandBase {

    public Drive() {
        addRequirements(DriveTrain.getInstance());
    }

    @Override
    public void initialize() {
        //SmartDashboard.putBoolean("DriveStraight?", false);
        //SmartDashboard.putBoolean("DriveDistance?", false);
        SmartDashboard.putBoolean("Drive?", true);
    }

    @Override
    public void execute() {
        double leftPower = powerCurve(OI.getINSTANCE().getLeftY());
        SmartDashboard.putNumber("Left Y", leftPower);
        double rightPower = powerCurve(OI.getINSTANCE().getRightY());
        if(Math.abs(IMU.getInstance().getPitch()) > 5){
            double correction = Math.min(IMU.getInstance().getPitch() / 10, 0.5);
            leftPower -= correction;
            rightPower -= correction;
        }
        DriveTrain.getInstance().tankDrive(leftPower, rightPower);

        /**if(DriveTrain.getInstance().aboveMaxTemp()){
            //release solenoid to cool motors down
            DriveTrain.getInstance().openTempSolenoid();
        }*/
    }

    private static double powerCurve(double x) {
        double curve = Math.pow(Math.sin(Math.PI / 2 * Math.abs(x)), RobotConfig.DRIVE.POWER_CURVE_EXPONENT) * Math.signum(x);
        return Math.copySign(curve, x);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Drive?", false);
    }
}
