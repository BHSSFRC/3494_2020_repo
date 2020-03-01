package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotConfig;
import frc.robot.commands.Shoot;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.commands.teleop.RunHopperMagazine;

public class FireTurnDrive extends SequentialCommandGroup {
    public FireTurnDrive() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new FooCommand(), new BarCommand());
        super(
                new Shoot(RobotConfig.FIRE_TURN_DRIVE.SHOOTER_POWER).withTimeout(RobotConfig.FIRE_TURN_DRIVE.SHOOT_TIMEOUT),
                new ParallelCommandGroup(
                        new Shoot(RobotConfig.FIRE_TURN_DRIVE.SHOOTER_POWER),
                        new RunHopperMagazine()).withTimeout(RobotConfig.FIRE_TURN_DRIVE.HOPPER_MAGAZINE_TIMEOUT),
                new TurnDegrees(RobotConfig.FIRE_TURN_DRIVE.TURN_AMOUNT_DEGREES)
                        .withTimeout(RobotConfig.FIRE_TURN_DRIVE.TURN_DEGREES_TIMEOUT),
                new DistanceDrive(40).withTimeout(RobotConfig.FIRE_TURN_DRIVE.DISTANCE_DRIVE_TIMEOUT)
        );
    }
}