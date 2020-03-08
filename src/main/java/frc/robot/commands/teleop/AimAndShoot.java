package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Shoot;
import frc.robot.commands.turret.AimBot;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;

public class AimAndShoot extends SequentialCommandGroup {
    public AimAndShoot(double targetRPM) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new InstantCommand(() -> System.out.println("Aim and Shoot--RPM: " + targetRPM)),
                new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.TWO)),
                new ParallelCommandGroup(
                        new AimBot(),
                        new Shoot(targetRPM, true)).withInterrupt(() -> Shooter.getInstance().atTargetSpeed(targetRPM) &&
                                                            Turret.getInstance().atCameraSetpoint()),
                new InstantCommand(() -> System.out.println("Turn on Hopper Magazine " + targetRPM)),
                new ParallelCommandGroup(
                        new Shoot(targetRPM, true),
                        new RunHopperMagazine()
                ).withTimeout(10),
                new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.TWO))
        );
    }
}