package frc.robot.commands.teleop;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.auto.RunIntakeAuto;

public class FloorPickup extends ParallelCommandGroup {
    public FloorPickup() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new IntakingRoutine(),
                new RunIntakeAuto()
        );
    }
}