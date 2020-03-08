package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotConfig;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.teleop.AimAndShoot;
import frc.robot.subsystems.Intake;

public class SixBallAuto extends SequentialCommandGroup {
    public SixBallAuto() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new AimAndShoot(),
                new InstantCommand(() -> Intake.getInstance().setDeployed(true)),
                new InstantCommand(() -> Intake.getInstance().runIntake(RobotConfig.MAGAZINE.INTAKE_DEFAULT_POWER)),
                new DistanceDrive(-30),
                new InstantCommand(() -> Intake.getInstance().setDeployed(false)),
                new InstantCommand(() -> Intake.getInstance().stop()),
                new DistanceDrive(30),
                new AimAndShoot()
        );
    }
}