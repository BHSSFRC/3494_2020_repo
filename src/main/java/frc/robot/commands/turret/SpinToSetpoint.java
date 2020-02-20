package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class SpinToSetpoint extends CommandBase {
    private double setpoint;
    private double power;
    private boolean alwaysUpdateSetpoint;
    private double degreesToRotate;
    private boolean spin;

    public SpinToSetpoint() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Turret.getInstance());
        this.power = 0.2;
        Turret.getInstance().setsetpoint(180);
        this.alwaysUpdateSetpoint = false;
        this.degreesToRotate = 0;
    }

    public SpinToSetpoint(boolean spin){
        addRequirements(Turret.getInstance());
        this.power = .2;
        this.alwaysUpdateSetpoint = false;
        this.spin = spin;
    }

    public SpinToSetpoint(double degreesToRotate){
        addRequirements(Turret.getInstance());
        this.degreesToRotate = degreesToRotate;
        //Turret.getInstance().setsetpoint(Turret.getInstance().getDegreesPosition() + degreesToRotate);
        this.alwaysUpdateSetpoint = true;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Go To Setpoint?", true);
        if(spin){
            Turret.getInstance().setsetpoint((SmartDashboard.getNumber("target-x", 0) - 150) / 300 * 270 + Turret.getInstance().getDegreesPosition());
        }
        if(this.alwaysUpdateSetpoint){
            Turret.getInstance().setsetpoint(Turret.getInstance().getDegreesPosition() + degreesToRotate);
        }else{
            Turret.getInstance().setsetpoint(180);
        }
        this.setpoint = Turret.getInstance().getSetpoint();
    }

    @Override
    public void execute() {
        /**if(this.alwaysUpdateSetpoint){
         double degreesOffset = SmartDashboard.getNumber("target-angle", 0);
         this.setpoint = Turret.getInstance().getDegreesPosition() + degreesOffset;
         Turret.getInstance().setsetpoint(this.setpoint);
         }*/
        double degreesOffset = Turret.getInstance().getDegreesPosition() - setpoint;
        this.power = 0.2;
        //this.power = Math.abs(degreesOffset) * 1 / Constants.TURRET.RANGE_OF_MOTION;
        /**this.power = Math.abs(degreesOffset) * 1 / 270;
         this.power = Math.max(Math.min(this.power, 1.0), 0.1);
         //if(Turret.getInstance().getDegreesPosition() < setpoint){*/
        if(degreesOffset < 0){
            Turret.getInstance().spin(-this.power);
        }else{
            Turret.getInstance().spin(this.power);
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        //return false;
        return Turret.getInstance().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Go To Setpoint?", false);
    }
}