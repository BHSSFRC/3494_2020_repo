package frc.robot.commands.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotConfig;
import frc.robot.RobotContainer;
import frc.robot.commands.RunHopper;
import frc.robot.commands.RunMagazine;
import frc.robot.subsystems.Hopper;
import frc.robot.Robot;

import java.awt.*;

public class IntakingRoutine extends SequentialCommandGroup {
    public IntakingRoutine() {
        super(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new RunMagazine(true, false, false).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken()),
                                new RunMagazine(true, true, false).withInterrupt(() -> Robot.getLinebreakTop().lineBroken()),
                                new RunMagazine(true, true, false).withTimeout(RobotConfig.MAGAZINE.TIME_AFTER_2ND_LINEBREAK_SENSOR),
                                new RunMagazine(true, false, false).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken())
                        )//,
                        //new RunHopper()
                ),
                //new InstantCommand(() -> Hopper.getInstance().stop()),
                new RunMagazine(false, false, false)
                /**new ParallelCommandGroup(
                        //new RunHopper(),
                        new RunMagazine(true, false, false)
                ).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken()), //withInterrupt(sensor 1 sees a ball)
                new ParallelCommandGroup(
                        //new RunHopper(),
                        new RunMagazine(true, true, false)
                ).withInterrupt(() -> Robot.getLinebreakTop().lineBroken()), //withInterrupt(sensor 2 sees a ball)
                new ParallelCommandGroup(
                        //new RunHopper(),
                        new RunMagazine(true, false, false)
                ).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken()), //withInterrupt(sensor 1 sees a ball, magazine is full)*/
                //new InstantCommand(() -> Hopper.getInstance().stop()),
        );
    }
}