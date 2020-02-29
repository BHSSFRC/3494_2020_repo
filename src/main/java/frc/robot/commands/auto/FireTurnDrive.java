package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.commands.teleop.RunHopperMagazine;
import frc.robot.subsystems.Shooter;

public class FireTurnDrive extends SequentialCommandGroup {
    public FireTurnDrive() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new InstantCommand(() -> Shooter.getInstance().shoot(0.7)).withTimeout(2),
                new ParallelCommandGroup(
                        new InstantCommand(() -> Shooter.getInstance().shoot(0.7)),
                        new RunHopperMagazine()).withTimeout(5),
                new InstantCommand(() -> Shooter.getInstance().stop()),
                new TurnDegrees(90),
                new DistanceDrive(40)
        );
    }
}