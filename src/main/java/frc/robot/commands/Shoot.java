package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.RobotConfig;
import frc.robot.subsystems.PreShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.util.QuadTimer;

public class Shoot extends CommandBase {

    int hoodPosition = 0;
    boolean releasedUp = true;
    boolean releasedDown = true;
    boolean doneMoving = true;
    boolean spinPreShooter;
    private double targetRPM;
    private QuadTimer timer;

    public Shoot() {
        addRequirements(Shooter.getInstance(), PreShooter.getInstance());
        this.spinPreShooter = false;
        /**this.targetRPM = OI.getINSTANCE().getXboxLeftTrigger() *
                SmartDashboard.getNumber("Shooter Max Power", 1) *
                RobotConfig.SHOOTER.RPM_PER_POWER;*/
        this.timer = new QuadTimer();
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Shoot?", true);
        timer.start();
    }

    @Override
    public void execute() {
        double shootPower = OI.getINSTANCE().getXboxLeftTrigger() *
                SmartDashboard.getNumber("Shooter Max Power", 1);
        Shooter.getInstance().shoot(shootPower);
        if (timer.get() > 0.5 && shootPower > 0.01) {
        //if(shootPower > 0.01){
            PreShooter.getInstance().spin(RobotConfig.SHOOTER.PRESHOOTER_POWER);
        }else{
            timer.reset();
            PreShooter.getInstance().stop();
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Shoot?", false);
        PreShooter.getInstance().stop();
        Shooter.getInstance().stop();
    }
}
