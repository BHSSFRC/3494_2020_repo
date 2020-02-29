package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.RobotMap;
import frc.robot.subsystems.Turret;

public class SpinToPosition extends CommandBase {
    private double setpoint;
    private double power;
    private boolean alwaysUpdateSetpoint;

    //when called without an argument, will spin to the center position of the turret
    public SpinToPosition() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Turret.getInstance());
        this.power = RobotConfig.TURRET.DEFAULT_SPIN_POWER;
        this.setpoint = RobotMap.TURRET.RANGE_OF_MOTION / 2;
    }

    public SpinToPosition(double degreesPosGoal){
        addRequirements(Turret.getInstance());
        this.power = RobotConfig.TURRET.DEFAULT_SPIN_POWER;
        this.setpoint = degreesPosGoal;
    }

    /**public SpinToSetpoint(double degreesToRotate){
        addRequirements(Turret.getInstance());
        this.power = RobotConfig.TURRET.DEFAULT_SPIN_POWER;
        this.setpoint = Turret.getInstance().getDegreesPosition() + degreesToRotate;
    }*/

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Go To Setpoint?", true);
        Turret.getInstance().setsetpoint(this.setpoint);
    }

    @Override
    public void execute() {
        double degreesOffset = Turret.getInstance().getDegreesPosition() - setpoint;
        if(degreesOffset < 0){
            Turret.getInstance().spin(-this.power);
        }else{
            Turret.getInstance().spin(this.power);
        }
    }

    @Override
    public boolean isFinished() {
        return Turret.getInstance().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Go To Setpoint?", false);
    }
}