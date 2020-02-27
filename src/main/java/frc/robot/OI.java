package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drive.Drive;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveClimb;
import frc.robot.commands.RollShooterPosition;
import frc.robot.commands.RunMagazine;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.RollShooterPosition;;
import frc.robot.commands.Shoot;

public class OI {
    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;
    private JoystickButton driveStraight;
    private JoystickButton driveTurn;
    private JoystickButton driveDistance;
    private JoystickButton runMagazine;
    private JoystickButton releaseClimber;
    private JoystickButton retractClimber;
    private JoystickButton extendClimber;
    private JoystickButton runShooter;
    private JoystickButton shooterPositionForward, shooterPositionBackward;

    private OI(){
        leftFlight = new Joystick(RobotMap.OI.LEFT_FLIGHT);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_FLIGHT);
        xbox = new XboxController(RobotMap.OI.XBOX);

        driveStraight = new JoystickButton(xbox, RobotMap.OI.DRIVE_STRAIGHT);
        driveStraight.whenPressed(new DriveStraight());
        driveStraight.whenReleased(new Drive());

        driveTurn = new JoystickButton(xbox, RobotMap.OI.DRIVE_TURN);
        driveTurn.whenPressed(new TurnDegrees(SmartDashboard.getNumber("Rotation(degrees)", 0)));

        driveDistance = new JoystickButton(xbox, RobotMap.OI.DRIVE_DISTANCE);
        //driveDistance.whenPressed(() -> SmartDashboard.putBoolean())
        driveDistance.whenPressed(new DistanceDrive(SmartDashboard.getNumber("Inches to Drive", 0)), false);

        runMagazine = new JoystickButton(xbox, RobotMap.OI.RUN_MAGAZINE);
        runMagazine.whenPressed(new RunMagazine(true, true, true));
        runMagazine.whenReleased(new RunMagazine(false, false, false));

        runShooter = new JoystickButton(xbox, RobotMap.OI.RUN_SHOOTER);
        shooterPositionBackward = new JoystickButton(xbox, RobotMap.OI.SHOOTER_BACKWARD);
        shooterPositionForward = new JoystickButton(xbox, RobotMap.OI.SHOOTER_FOWARD);
        runShooter.whileHeld(new Shoot());
        //shooterPositionForward.whenPressed(new RollShooterPosition(true));
        //shooterPositionBackward.whenPressed(new RollShooterPosition(false));
        //runMagazine.whenPressed(new RunHopper());
        //runMagazine.whenReleased(new InstantCommand(() -> Hopper.getInstance().stop()));

        
        releaseClimber = new JoystickButton(xbox, RobotMap.OI.RELEASE_BUTTON);
        retractClimber = new JoystickButton(xbox, RobotMap.OI.DRIVE_BUTTON);
        extendClimber = new JoystickButton(xbox, RobotMap.OI.DRIVE_BUTTON);
        //- = down
        //+ = up
        releaseClimber.whenPressed(new Climb());
        retractClimber.whenPressed(new DriveClimb(-RobotMap.CLIMBER.CLIMB_POWER));
        extendClimber.whenPressed(new DriveClimb(RobotMap.CLIMBER.CLIMB_POWER));
    }

    public double getLeftY(){
        return removeDeadband(leftFlight.getY(GenericHID.Hand.kLeft));
    }

    public double getRightY(){
        return removeDeadband(rightFlight.getY(GenericHID.Hand.kRight));
    }

    public double getXboxRightY(){
        return this.xbox.getY(GenericHID.Hand.kRight);
    }

    public double getXboxRightX() {
        return this.xbox.getX(GenericHID.Hand.kRight);
    }

    public double getXboxRightTrigger(){
        return this.xbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getXboxLeftTrigger(){
        return this.xbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public boolean getXboxRightBumper(){
        return this.xbox.getBumper(GenericHID.Hand.kRight);
    }

    public boolean getXboxLeftBumper(){
        return this.xbox.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean getXboxDpadUp(){
        return this.xbox.getPOV() == 0;
    }

    public boolean getXboxDpadDown(){
        return this.xbox.getPOV() == 180;
    }
    /**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     * @author WPI
     */
    public static double removeDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }
    public static double removeDeadband(double value) {
        if (Math.abs(value) > .05) {
            if (value > 0.0) {
                return (value - .05) / (1.0 - .05);
            } else {
                return (value + .05) / (1.0 - .05);
            }
        } else {
            return 0.0;
        }
    }

    public static OI getINSTANCE(){
        return INSTANCE;
    }
}
