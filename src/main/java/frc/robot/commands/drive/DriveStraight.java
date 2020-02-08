package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.OI;
import frc.robot.RobotConfig;
import frc.robot.RobotMap;
import frc.robot.commands.Drive;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;
import frc.robot.util.QuadTimer;
import frc.robot.util.SynchronousPIDF;

public class DriveStraight extends CommandBase {
    private QuadTimer timer;
    private SynchronousPIDF pidController;
    private double power;

    public DriveStraight() {
        //super(new PIDController());
        //super(1.0,1.0,1.0);
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(DriveTrain.getInstance());
        this.timer = new QuadTimer();
        this.initializePID();
        this.power = 0;
    }

    private void initializePID(){
        //takes same PID constants as DriveAntitipPD does. if necessary will add new set of constants
        double kp;
        double ki;
        double kd;
        //if (SmartDashboard.containsKey("Display Drivetrain data?")) {
        if(true){
            kp = SmartDashboard.getNumber("Tuning/PID P", 0.9);
            ki = SmartDashboard.getNumber("Tuning/PID I", 0.0);
            kd = SmartDashboard.getNumber("Tuning/PID D", 0.0);
        }else{
            kp = RobotConfig.DRIVE_STRAIGHT.kP;
            ki = RobotConfig.DRIVE_STRAIGHT.kI;
            kd = RobotConfig.DRIVE_STRAIGHT.kD;
        }

        this.pidController = new SynchronousPIDF(kp, ki, kd);
        pidController.setPID(kp, ki, kd);
        pidController.setInputRange(0, 360);
        pidController.setContinuous(true);
        pidController.setOutputRange(-1, 1);
    }

    @Override
    public void initialize() {
        this.timer.start();
        pidController.setSetpoint(IMU.getInstance().getYaw());
        SmartDashboard.putBoolean("DriveStraight?", true);
    }

    @Override
    public void execute() {
        double input = IMU.getInstance().getYaw();
        power = OI.getINSTANCE().getLeftY();
        double output = this.pidController.calculate(input, this.timer.delta());
        SmartDashboard.putNumber("DriveStraight Offset", output);
        DriveTrain.getInstance().tankDrive(power + output, power - output);
    }

    protected double returnPIDInput() {
        return 0;
        // returns the sensor value that is providing the feedback for the system
    }

    protected void usePIDOutput(double output) {
        // this is where the computed output value fromthe PIDController is applied to the motor
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    public void end() {
        SmartDashboard.putBoolean("DriveStraight?", false);
        DriveTrain.getInstance().stop();
    }

    public void interrupted() {

    }
}
