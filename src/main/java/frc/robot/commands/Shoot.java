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
    private double targetRPM;
    private QuadTimer timer;
    private double shootPower;
    private boolean fixedSpeed;
    private boolean goToRPM;

    public Shoot() {
        addRequirements(Shooter.getInstance(), PreShooter.getInstance());
        /**this.targetRPM = OI.getINSTANCE().getXboxLeftTrigger() *
                SmartDashboard.getNumber("Shooter Max Power", 1) *
                RobotConfig.SHOOTER.RPM_PER_POWER;*/
        this.timer = new QuadTimer();
        this.fixedSpeed = false;
        this.targetRPM = SmartDashboard.getNumber("Shooter RPM Target", -1);
        this.goToRPM = false;
    }

    public Shoot(double fixedPower) {
        addRequirements(Shooter.getInstance(), PreShooter.getInstance());
        this.timer = new QuadTimer();
        this.fixedSpeed = true;
        this.shootPower = fixedPower;
        this.targetRPM = -1;
        this.goToRPM = false;
    }

    public Shoot(double targetRPM, boolean RPM){
        addRequirements(Shooter.getInstance(), PreShooter.getInstance());
        this.targetRPM = targetRPM;
        System.out.println("Shoot at RPM: " + targetRPM);
        this.fixedSpeed = true;
        this.shootPower = 0.5;
        this.timer = new QuadTimer();
        this.goToRPM = true;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Shoot?", true);
        timer.start();
    }

    @Override
    public void execute() {
        if(!this.fixedSpeed){
            if(SmartDashboard.getNumber("Shooter Max Power", .8) != -1){
                shootPower = OI.getINSTANCE().getXboxLeftTrigger() *
                        SmartDashboard.getNumber("Shooter Max Power", 1);
            }else{
                shootPower = OI.getINSTANCE().getXboxLeftTrigger() *
                        RobotConfig.SHOOTER.SHOOTER_MAX_POWER;
            }
        }

        if(this.goToRPM || (shootPower > 0.05 && targetRPM != -1)){
            //Shooter.getInstance().shoot(shootPower);
            Shooter.getInstance().setRPM(targetRPM);
        }else{
            Shooter.getInstance().shoot(shootPower);
        }

        if (timer.get() > RobotConfig.SHOOTER.PRESHOOTER_DELAY && shootPower > 0.05) {
        //if(shootPower > 0.05){
            System.out.println("Time: " + timer.get());
            PreShooter.getInstance().spin(SmartDashboard.getNumber("Preshooter Power", RobotConfig.SHOOTER.PRESHOOTER_POWER));
        }else if (shootPower < .05 && !this.goToRPM) {
            timer.reset();
            PreShooter.getInstance().stop();
            //System.out.println("stop preshooter");
        }else{
            System.out.println("powering up shooter: " + timer.get());
            //System.out.println("stop preshooter");
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
