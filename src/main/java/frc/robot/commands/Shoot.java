package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Shooter;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Shoot extends CommandBase {

    int hoodPosition = 0;
    boolean releasedUp = true;
    boolean releasedDown = true;
    boolean doneMoving = true;

    ScheduledThreadPoolExecutor delayer;

    public Shoot() {
        // If any subsystems are needed, you will need to pass them into the requires() method
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Shoot?", true);
        delayer = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public void execute() {
        Shooter.getInstance().shoot(OI.getINSTANCE().getXboxLeftTrigger() * SmartDashboard.getNumber("Shooter Max Power", 1));
        if (releasedUp && OI.getINSTANCE().getXboxDpadUp() && doneMoving) {
            if (hoodPosition < 2) hoodPosition++;
            resetPosition();
            releasedUp = false;
        }
        if (!releasedUp && !OI.getINSTANCE().getXboxDpadUp()) releasedUp = true;
        if (releasedDown && OI.getINSTANCE().getXboxDpadDown() && doneMoving) {
            if (hoodPosition > 0) hoodPosition--;
            resetPosition();

            delayer.schedule(new Runnable() {
                public void run() {
                    if (hoodPosition == 0) {
                        Shooter.getInstance().hood(false, false);
                        delayer.schedule(new Runnable() {
                            public void run() {
                                Shooter.getInstance().hood(true, true);
                                doneMoving = true;
                            }
                        }, 50, TimeUnit.MILLISECONDS);
                        doneMoving = true;
                    }
                    if (hoodPosition == 1) {
                        Shooter.getInstance().hood(false, true);
                        delayer.schedule(new Runnable() {
                            public void run() {
                                Shooter.getInstance().hood(true, true);
                                doneMoving = true;
                            }
                        }, 50, TimeUnit.MILLISECONDS);
                    }
                    if (hoodPosition == 2) {
                        Shooter.getInstance().hood(true, false);
                        doneMoving = true;
                    }
                }
            }, 50, TimeUnit.MILLISECONDS);
            releasedUp = false;
        }
        if (!releasedDown && !OI.getINSTANCE().getXboxDpadUp()) releasedDown = true;

        if (hoodPosition == 0 && doneMoving) Shooter.getInstance().hood(false, false);
        if (hoodPosition == 1 && doneMoving) Shooter.getInstance().hood(true, true);
        if (hoodPosition == 2 && doneMoving) Shooter.getInstance().hood(true, false);
    }

    private void resetPosition() {
        Shooter.getInstance().hood(false, false);
        doneMoving = false;
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Shoot?", false);
        Shooter.getInstance().shoot(0);
    }
}
