package frc.robot.commands.teleop;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
import frc.robot.subsystems.Magazine;

import java.awt.*;

public class IntakingRoutine extends SequentialCommandGroup {
    public IntakingRoutine(Magazine mag, Hopper hop) {
        SmartDashboard.putBoolean("Intaking Routine", true);
        System.out.println("Start Intaking Routine");
        addCommands(
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new RunMagazine(true, false, false).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken()),
                                new InstantCommand(() -> System.out.println("First Linebreak Sensor Tripped")),
                                new RunMagazine(true, true, false).withInterrupt(() -> Robot.getLinebreakTop().lineBroken()),
                                new InstantCommand(() -> System.out.println("Second Linebreak Sensor Tripped, wait 1 second")),
                                new RunMagazine(true, true, false).withTimeout(RobotConfig.MAGAZINE.TIME_AFTER_2ND_LINEBREAK_SENSOR),
                                new InstantCommand(() -> System.out.println("1 second passed")),
                                new RunMagazine(true, false, false).withInterrupt(() -> Robot.getLinebreakBottom().lineBroken()),
                                new InstantCommand(() -> System.out.println("First Linebreak Sensor Tripped, Finish"))
                        ),
                        new RunHopper()
                ),
                new InstantCommand(() -> Hopper.getInstance().stop()),
                new InstantCommand(() -> Magazine.getInstance().stop()),
                new InstantCommand(() -> System.out.println("Intaking Routine Finished"))
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
        SmartDashboard.putBoolean("Intaking Routine", false);
    }
}