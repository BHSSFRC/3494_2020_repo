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
import frc.robot.subsystems.DriveTrain;
import frc.robot.util.QuadTimer;

public class DriveStraight extends CommandBase {
    private QuadTimer timer;
    private PIDController pidController;
    private double power;

    public DriveStraight() {
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
        if (SmartDashboard.containsKey("Display Drivetrain data?")) {
            kp = SmartDashboard.getNumber("Tuning/PID P", 0.9);
            ki = SmartDashboard.getNumber("Tuning/PID I", 0.0);
            kd = SmartDashboard.getNumber("Tuning/PID D", 0.0);
        }else{
            kp = RobotConfig.DRIVE_STRAIGHT.kP;
            ki = RobotConfig.DRIVE_STRAIGHT.kI;
            kd = RobotConfig.DRIVE_STRAIGHT.kD;
        }
        this.pidController = new PIDController(kp, ki, kd);
        pidController.setPID(kp, ki, kd);
        pidController.enableContinuousInput(0, 2 * Math.PI);
        pidController.setSetpoint(0);
        //pidController.setSetpoint(); set to current heading
    }

    public double returnPIDInput(){
        return 0.0;
        //TODO: should return the current angle of the robot
    }

    @Override
    public void initialize() {
        this.timer.start();
    }

    @Override
    public void execute() {
        power = OI.getINSTANCE().getLeftY();
        //heading should equal the gyro readout
        double heading = 0;
        double output = this.pidController.calculate(heading);
        DriveTrain.getInstance().tankDrive(power + output, power - output);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    public void end() {
        DriveTrain.getInstance().stop();
    }

    public void interrupted() {

    }
}
