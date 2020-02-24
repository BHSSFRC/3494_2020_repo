package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Magazine;


public class RunMagazine extends CommandBase {
    private boolean runFront;
    private boolean runBottom;
    private boolean runTop;

    public RunMagazine() {
        this(false, false, false);
    }

    public RunMagazine(boolean front, boolean bottom, boolean top){
        addRequirements(Magazine.getInstance());
        this.runFront = front;
        this.runBottom = bottom;
        this.runTop = top;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        Magazine.getInstance().runFront(this.runFront);
        Magazine.getInstance().runBottom(this.runBottom);
        Magazine.getInstance().runTop(this.runTop);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Magazine.getInstance().stop();
    }
}
