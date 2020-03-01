package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.teleop.IntakingRoutine;
import frc.robot.commands.teleop.ReverseHopper;
import frc.robot.commands.teleop.StopHopperMagazine;
import frc.robot.commands.turret.AimBot;
import frc.robot.commands.turret.QuickTurretLimit;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;

public class OI {
    private static OI INSTANCE = new OI();
    private XboxController primaryXbox;
    private XboxController secondaryXbox;

    private JoystickButton runMagazine;

    private JoystickButton runShooter;
    private JoystickButton shooterPositionForward, shooterPositionBackward;
    private JoystickButton runHopper;
    private Trigger releaseClimber;
    private Trigger retractClimber;
    private Trigger reverseClimberSlow;
    private Trigger extendClimber;
    private JoystickButton safetyClimber;
    private JoystickButton intakingRoutine;
    private JoystickButton spinHopperMagazine;
    private Trigger stopHopperMagazine;
    private JoystickButton reverseHopper;
    //private JoystickButton shooterHood;
    //private JoystickButton shooterLimit;
    private JoystickButton quickTurretLimits;
    private JoystickButton aimBot;
    private JoystickButton turretToStartPos;
    private Trigger shooterLow;
    private Trigger shooterMed;
    private Trigger shooterHigh;
    private Trigger leftTriggerPressed;

    private JoystickButton toggleLED;

    private ButtonBoard bb;
    private JoystickButton[] boardButtons;

    /**
     * Adam
     * Intake right trigger
     * Intake pneumatics: right bumper
     * shooting power left trigger
     * Magazine/hopper feed to shooter button b
     * Climber:
     * release = 12
     * climb = 14
     * retract = 13
     * shooter speed limit
     * short = 1
     * medium = 3
     * long = 6
     */

    /**
     * shooter right trigger analog power
     * pneumatics
     * magazine/hopper: manual left bumper
     * intake: left trigger analog power
     * climber: button board
     */

    private OI(){
        primaryXbox = new XboxController(RobotMap.OI.PRIMARY_XBOX);
        secondaryXbox = new XboxController(RobotMap.OI.SECONDARY_XBOX);

        bb = new ButtonBoard(RobotMap.OI.BUTTON_BOARD);
        boardButtons = new JoystickButton[15];

        reverseHopper = new JoystickButton(bb, RobotMap.OI.REVERSE_HOPPER);
        reverseHopper.whileHeld(new ReverseHopper());

        runMagazine = new JoystickButton(bb, RobotMap.OI.RUN_MAGAZINE);
        runMagazine.whenPressed(new RunMagazine(true, true, true));
        runMagazine.whenReleased(new RunMagazine(false, false, false));

        runShooter = new JoystickButton(secondaryXbox, RobotMap.OI.RUN_SHOOTER);
        runShooter.whileHeld(new Shoot());

        runHopper = new JoystickButton(bb, RobotMap.OI.RUN_HOPPER);
        runHopper.whenPressed(new RunHopper());

        quickTurretLimits = new JoystickButton(bb, RobotMap.OI.QUICK_TURRET_LIMITS);
        quickTurretLimits.whenPressed(new QuickTurretLimit());

        aimBot = new JoystickButton(bb, RobotMap.OI.AIM_BOT);
        aimBot.toggleWhenPressed(new AimBot());
        //turretToStartPos = new JoystickButton(bb, RobotMap.OI.TURRET_TO_START_POS);
        //turretToStartPos.whenPressed(new SpinToPosition(0.0));

        safetyClimber = new JoystickButton(bb, RobotMap.OI.SAFETY_CLIMBER);
        retractClimber = new JoystickButton(bb, RobotMap.OI.REVERSE_CLIMBER).and(safetyClimber);
        reverseClimberSlow = new JoystickButton(bb, RobotMap.OI.REVERSE_CLIMBER_SLOW).and(safetyClimber);
        extendClimber = new JoystickButton(bb, RobotMap.OI.DRIVE_CLIMBER).and(safetyClimber);
        releaseClimber = new JoystickButton(bb, RobotMap.OI.RELEASE_CLIMBER).and(safetyClimber);
        //- = down
        //+ = up
        releaseClimber.whenActive(new Climb());
        retractClimber.whileActiveContinuous(new DriveClimb(RobotMap.CLIMBER.CLIMB_DOWN_POWER));
        reverseClimberSlow.whileActiveContinuous(new DriveClimb(RobotMap.CLIMBER.CLIMB_DOWN_SLOW_POWER));
        extendClimber.whileActiveContinuous(new DriveClimb(RobotMap.CLIMBER.CLIMB_UP_POWER));

        //intakingRoutine = new Trigger(() -> this.getXboxLeftBumperPressed());
        intakingRoutine = new JoystickButton(bb, RobotMap.OI.SPIN_HOPPER_MAGAZINE);
        intakingRoutine.whenActive(new IntakingRoutine(Magazine.getInstance(), Hopper.getInstance()));
        //spinHopperMagazine = new JoystickButton(bb, RobotMap.OI.SPIN_HOPPER_MAGAZINE);
        //spinHopperMagazine = new Trigger(() -> this.getXboxLeftBumperPressed());
        spinHopperMagazine = new JoystickButton(secondaryXbox, RobotMap.OI.SPIN_HOPPER_MAGAZINE);
        //spinHopperMagazine.whenPressed(new RunHopper(), new RunMagazine());
        spinHopperMagazine.whenPressed(new ParallelCommandGroup(new RunHopper(), new RunMagazine(true, true, false)));
        spinHopperMagazine.whenReleased(new StopHopperMagazine());

        //stopHopperMagazine = new Trigger(() -> this.getXboxLeftBumperReleased());
        //stopHopperMagazine.whenActive(new SequentialCommandGroup(new InstantCommand(() -> Magazine.getInstance().stop()),
        //        new InstantCommand(() -> Hopper.getInstance().stop())));

        shooterPositionBackward = new JoystickButton(secondaryXbox, RobotMap.OI.SHOOTER_BACKWARD);
        shooterPositionBackward.whenPressed(new RollShooterPosition(false));
        shooterPositionForward = new JoystickButton(secondaryXbox, RobotMap.OI.SHOOTER_FORWARD);
        shooterPositionForward.whenPressed(new RollShooterPosition(true));

        toggleLED = new JoystickButton(bb, RobotMap.OI.TOGGLE_LED);
        toggleLED.whenPressed(new InstantCommand(() -> DriveTrain.getInstance().toggleLED()));

        leftTriggerPressed = new Trigger(() -> getSecondaryXboxLeftTriggerPressed());
        leftTriggerPressed.whenInactive(new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.ONE)));
        shooterLow = new JoystickButton(bb, RobotMap.OI.SHOOTER_LOW).and(leftTriggerPressed);
        shooterMed = new JoystickButton(bb, RobotMap.OI.SHOOTER_MED).and(leftTriggerPressed);
        shooterHigh = new JoystickButton(bb, RobotMap.OI.SHOOTER_HIGH).and(leftTriggerPressed);

        shooterLow.whenActive(new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.ONE)));
        shooterMed.whenActive(new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.TWO)));
        shooterHigh.whenActive(new InstantCommand(() -> Shooter.getInstance().setPosition(Shooter.Position.THREE)));

        /**shooterLow = new JoystickButton(bb, RobotMap.OI.SHOOTER_LOW);
        shooterLow.whenPressed(new InstantCommand(() -> RobotConfig.SHOOTER.SHOOTER_MAX_POWER = .2));
        //shooterLow.whenPressed(new InstantCommand(() ->
        //        SmartDashboard.putNumber("Shooter Max Power", SmartDashboard.getNumber("S Low", .2))));
        shooterMed = new JoystickButton(bb, RobotMap.OI.SHOOTER_MED);
        shooterMed.whenPressed(new InstantCommand(() -> RobotConfig.SHOOTER.SHOOTER_MAX_POWER = .65));
        //shooterLow.whenPressed(new InstantCommand(() ->
        //        SmartDashboard.putNumber("Shooter Max Power", SmartDashboard.getNumber("S Med", .2))));
        shooterHigh = new JoystickButton(bb, RobotMap.OI.SHOOTER_HIGH);
        shooterHigh.whenPressed(new InstantCommand(() -> RobotConfig.SHOOTER.SHOOTER_MAX_POWER = .8));
        //shooterLow.whenPressed(new InstantCommand(() ->
        //        SmartDashboard.putNumber("Shooter Max Power", SmartDashboard.getNumber("S High", .2))));*/
    }

    /**public double getLeftY(){
        return removeDeadband(leftFlight.getY(GenericHID.Hand.kLeft));
    }

    public double getRightY(){
        return removeDeadband(rightFlight.getY(GenericHID.Hand.kRight));
    }*/

    public double getPrimaryXboxLeftTrigger(){
        return this.primaryXbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public double getPrimaryXboxRightTrigger(){
        return this.primaryXbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getPrimaryXboxLeftX(){
        return this.primaryXbox.getX(GenericHID.Hand.kLeft);
    }

    public boolean getPrimaryXboxA(){
        return this.primaryXbox.getAButton();
    }

    public boolean getSecondaryXboxB(){
        return this.secondaryXbox.getAButton();
    }

    public double getXboxRightY(){
        return this.secondaryXbox.getY(GenericHID.Hand.kRight);
    }

    public double getXboxRightX() {
        return this.secondaryXbox.getX(GenericHID.Hand.kRight);
    }

    public double getXboxRightTrigger(){
        return this.secondaryXbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getXboxLeftTrigger(){
        return this.secondaryXbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public boolean getXboxRightBumper(){
        return this.secondaryXbox.getBumper(GenericHID.Hand.kRight);
    }

    public boolean getXboxLeftBumper(){
        return this.secondaryXbox.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean getXboxLeftBumperPressed(){
        return this.secondaryXbox.getBumperPressed(GenericHID.Hand.kLeft);
    }

    public boolean getXboxLeftBumperReleased(){
        return this.secondaryXbox.getBumperReleased(GenericHID.Hand.kLeft);
    }

    public boolean getSecondaryXboxLeftTriggerPressed(){
        return this.secondaryXbox.getTriggerAxis(GenericHID.Hand.kLeft) > 0.05;
    }

    public boolean getXboxDpadUp(){
        return this.secondaryXbox.getPOV() == 0;
    }

    public boolean getXboxDpadDown(){
        return this.secondaryXbox.getPOV() == 180;
    }

    public boolean getXboxDpadLeft(){
        return this.secondaryXbox.getPOV() == 270;
    }

    public boolean getXboxDpadRight(){
        return this.secondaryXbox.getPOV() == 90;
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
