package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Shoot;
import frc.robot.commands.teleop.RunHopperMagazine;

public class FireTurnDrive extends SequentialCommandGroup {
    public FireTurnDrive() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new Shoot(0.65).withTimeout(2),
                new ParallelCommandGroup(
                        new Shoot(0.65),
                        new RunHopperMagazine()).withTimeout(5)
                //new TurnDegrees(80).withTimeout(4),
                //new DistanceDrive(40)
        );
    }
}