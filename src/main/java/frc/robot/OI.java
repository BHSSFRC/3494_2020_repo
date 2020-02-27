package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.commands.teleop.IntakingRoutine;

public class OI {
    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;
    private JoystickButton driveStraight;
    private JoystickButton driveTurn;
    private JoystickButton driveDistance;
    private JoystickButton runMagazine;
    private JoystickButton runHopper;
    private Trigger releaseClimber;
    private Trigger retractClimber;
    private Trigger extendClimber;
    private JoystickButton intakingRoutine;
    private JoystickButton spinHopperMagazine;
    private JoystickButton shooterHood;
    private JoystickButton shooterLimit;

    private ButtonBoard bb;
    private JoystickButton[] boardButtons;

    /**
     * Adam
     * Intake right trigger
     * Intake pneumatics: right bumper
     * shooting power left trigger
     * Magazine/hopper feed to shooter button b
     *
     */

    /**
     * shooter right trigger analog power
     * pneumatics
     * magazine/hopper: manual left bumper
     * intake: left trigger analog power
     * climber: button board
     */
    //TODO: display COLOR on SmartDashboard

    private OI(){
        leftFlight = new Joystick(RobotMap.OI.LEFT_FLIGHT);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_FLIGHT);
        xbox = new XboxController(RobotMap.OI.XBOX);

        bb = new ButtonBoard(RobotMap.OI.BUTTON_BOARD);
        boardButtons = new JoystickButton[15];

        driveStraight = new JoystickButton(bb, RobotMap.OI.DRIVE_STRAIGHT);
        driveStraight.whenPressed(new DriveStraight());
        driveStraight.whenReleased(new Drive());
        driveTurn = new JoystickButton(bb, RobotMap.OI.DRIVE_TURN);
        driveTurn.whenPressed(new TurnDegrees(SmartDashboard.getNumber("Rotation(degrees)", 0)));
        driveDistance = new JoystickButton(bb, RobotMap.OI.DRIVE_DISTANCE);
        //driveDistance.whenPressed(() -> SmartDashboard.putBoolean())
        driveDistance.whenPressed(new DistanceDrive(SmartDashboard.getNumber("Inches to Drive", 0)), false);

        spinHopperMagazine = new JoystickButton(bb, RobotMap.OI.SPIN_HOPPER_MAGAZINE);
        spinHopperMagazine.whenPressed(new ParallelCommandGroup(new RunHopper(), new RunMagazine(true, true, false)));

        runMagazine = new JoystickButton(bb, RobotMap.OI.RUN_MAGAZINE);
        runMagazine.whenPressed(new RunMagazine(true, true, true));
        runMagazine.whenReleased(new RunMagazine(false, false, false));

        runHopper = new JoystickButton(bb, RobotMap.OI.RUN_HOPPER);
        runHopper.whenPressed(new RunHopper());
        
        /**releaseClimber = new JoystickButton(bb, RobotMap.OI.RELEASE_CLIMBER);
        retractClimber = new JoystickButton(bb, RobotMap.OI.REVERSE_CLIMBER);
        extendClimber = new JoystickButton(bb, RobotMap.OI.DRIVE_CLIMBER);*/
        retractClimber = new Trigger(() -> getXboxDpadDown());
        extendClimber = new Trigger(() -> getXboxDpadUp());
        releaseClimber = new Trigger(() -> getXboxDpadLeft());
        //- = down
        //+ = up
        releaseClimber.whenActive(new Climb());
        retractClimber.whenActive(new DriveClimb(-RobotMap.CLIMBER.CLIMB_UP_POWER));
        extendClimber.whenActive(new DriveClimb(RobotMap.CLIMBER.CLIMB_UP_POWER));

        intakingRoutine = new JoystickButton(xbox, RobotMap.OI.INTAKING_ROUTINE);
        intakingRoutine.whenPressed(new IntakingRoutine());

        //this.shooterHood = new JoystickButton(xbox, RobotMap.OI.)
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

    public boolean getXboxDpadLeft(){
        return this.xbox.getPOV() == 270;
    }

    public boolean getXboxDpadRight(){
        return this.xbox.getPOV() == 90;
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
