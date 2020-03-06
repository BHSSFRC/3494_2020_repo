package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotConfig;
import frc.robot.subsystems.Turret;

public class AimBot extends CommandBase {
    private double power;
    private double targetOffset;

    public AimBot() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Turret.getInstance());
        this.power = RobotConfig.TURRET.DEFAULT_SPIN_POWER;
        this.targetOffset = 0;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.targetOffset = SmartDashboard.getNumber("target-x", 0);
        if(targetOffset < 1.0 && targetOffset > -1.0){
            this.power = Math.abs(this.targetOffset) * .40;
        }else{
            this.power = 0;
        }

        this.power = Math.min(this.power, 0.2);

        //SmartDashboard.putNumber("Pixel Offset", pixelsOffset);
        if(this.targetOffset < 0){
            Turret.getInstance().spin(this.power);
        }else{
            Turret.getInstance().spin(-this.power);
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrrupted) {

    }
}