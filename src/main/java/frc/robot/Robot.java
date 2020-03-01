/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunMagazine;
import frc.robot.commands.Shoot;
import frc.robot.commands.drive.Drive;
import frc.robot.commands.turret.SpinTurret;
import frc.robot.sensors.IMU;
import frc.robot.sensors.Linebreaker;
import frc.robot.subsystems.*;
import org.opencv.core.Mat;


/**
 * The VM is configured to automatically run this class, and to call the
 * methods corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;

    private RobotContainer robotContainer;

    private static Linebreaker bottom;
    private static Linebreaker top;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();

        bottom = new Linebreaker(RobotMap.SENSORS.LINEBREAK_BOT);
        top = new Linebreaker(RobotMap.SENSORS.LINEBREAK_TOP);

        //remove SmartDash keys
        /**String[] smartDashKeys = SmartDashboard.getKeys().toArray(new String[0]);
        for (String key : smartDashKeys){
            SmartDashboard.delete(key);
        }*/

        String[] SDDoubles = {"Shooter Max Power", "Angle", "Calibrate1", "Calibrate2", "TurnPower",
                "Encoder Distance", "Inches to Drive", "Rotation(degrees)", "target-x", "target-y", "Turret Pos", "Pos Degrees",
                "Shooter RPM", "Shooter Power Current", "Drive Max Power", "Gain/FSP", "Hopper Power", "Magazine Power"};

        for (String doubleName : SDDoubles) {
            if (!SmartDashboard.containsKey(doubleName)) {
                SmartDashboard.putNumber(doubleName, 1);
                SmartDashboard.setPersistent(doubleName);
            }
        }

        //If either of these lines are uncommented out, the null exception error shows
        CommandScheduler.getInstance().setDefaultCommand(Shooter.getInstance(), new Shoot());
        CommandScheduler.getInstance().setDefaultCommand(Intake.getInstance(), new RunIntake());
        CommandScheduler.getInstance().setDefaultCommand(Turret.getInstance(), new SpinTurret());
        CommandScheduler.getInstance().setDefaultCommand(DriveTrain.getInstance(), new Drive());

        CommandScheduler.getInstance().setDefaultCommand(Magazine.getInstance(), new RunMagazine());

        CommandScheduler.getInstance().schedule(new InstantCommand(Pneumatics.getInstance()::startCompressor));

        String[] SDBooleans = {"Calibrate IMU?",
        "Front Limit", "Back Limit", "Auto Forward?"};

        for (String booleanName : SDBooleans) {
            if (!SmartDashboard.containsKey(booleanName)) {
                SmartDashboard.putBoolean(booleanName, false);
                SmartDashboard.setPersistent(booleanName);
            }
        }

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, 320, 240, 120);
        camera.setResolution(320, 240);
        new Thread(() -> {
                CvSink cvSink = CameraServer.getInstance().getVideo();
                CvSource outputStream = CameraServer.getInstance().putVideo("camera stream", 320, 240);
                Mat source = new Mat();
                while(!Thread.interrupted()) {
                    cvSink.grabFrame(source);
                    outputStream.putFrame(source);
                    }
        }).start();
    }

    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();

        //SmartDashboard.putBoolean("Dist Sensor Error", Dist2m.getInstance().isNotEnabled());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        //Command teleopCommand = robotContainer.getTeleopCommand();
        //teleopCommand.schedule();
        //new Drive();
        //new ScheduleCommand(new Drive());
        //CommandScheduler.getInstance().schedule(new Drive());
        //CommandScheduler.getInstance().schedule(new Drive());
        /**if (SmartDashboard.getBoolean("Calibrate IMU?", false)) {
            CommandScheduler.getInstance().schedule(new CalibrateIMU());
        }*/
    }

    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Angle", IMU.getInstance().getYaw());
        /**SmartDashboard.putNumber("Shooter RPM", Shooter.getInstance().getRPM());
        SmartDashboard.putNumber("Turret Pos", Turret.getInstance().getPosition());*/
        //SmartDashboard.putBoolean("Front Limit", Turret.getInstance().atFrontLimit());
        //SmartDashboard.putBoolean("Back Limit", Turret.getInstance().atBackLimit());
        SmartDashboard.putNumber("Gain/FSP", RobotConfig.SHOOTER.SHOOTER_MAX_POWER);
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    public static Linebreaker getLinebreakBottom() {
        return bottom;
    }

    public static Linebreaker getLinebreakTop() {
        return top;
    }
}
