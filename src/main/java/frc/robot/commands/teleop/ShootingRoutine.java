package frc.robot.commands.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.RunHopper;
import frc.robot.commands.RunMagazine;
import frc.robot.subsystems.Hopper;

public class ShootingRoutine extends SequentialCommandGroup {
    public ShootingRoutine() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new ParallelCommandGroup(
                        new RunHopper(),
                        new RunMagazine(true, false, false)
                ), //withInterrupt(sensor 1 sees a ball)
                new ParallelCommandGroup(
                        new RunHopper(),
                        new RunMagazine(true, true, false)
                ), //withInterrupt(sensor 2 sees a ball)
                new ParallelCommandGroup(
                        new RunHopper(),
                        new RunMagazine(true, false, false)
                ), //withInterrupt(sensor 1 sees a ball, magazine is full)
                /**new ParallelCommandGroup(
                        new InstantCommand(() -> Hopper.getInstance().stop()),
                        new RunMagazine(false, false, false);
                )*/
                new InstantCommand(() -> Hopper.getInstance().stop()),
                new RunMagazine(false, false, false)
        );
    }
}