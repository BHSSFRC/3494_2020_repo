package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Shoot;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.teleop.AimAndShoot;
import frc.robot.subsystems.Intake;

public class SixBallAuto extends SequentialCommandGroup {
    public SixBallAuto() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new InstantCommand(() -> Intake.getInstance().setDeployed(true)),
                new AimAndShoot(3),
                new ParallelDeadlineGroup(
                        new DistanceDrive(-80),
                        new RunIntakeAuto()
                ),
                new RunIntakeAuto().withTimeout(0.5),
                new ParallelDeadlineGroup(
                        new DistanceDrive(80),
                        new Shoot(SmartDashboard.getNumber("Shooter RPM Target", 0), true)),
                new AimAndShoot(3)
        );
    }
}