package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.teleop.AimAndShoot;

public class SixBallAuto extends SequentialCommandGroup {
    public SixBallAuto() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new AimAndShoot(),
                new ParallelDeadlineGroup(
                        new DistanceDrive(-50),
                        new RunIntakeAuto()
                ),
                new RunIntakeAuto().withTimeout(2),
                new DistanceDrive(50),
                new AimAndShoot()
        );
    }
}