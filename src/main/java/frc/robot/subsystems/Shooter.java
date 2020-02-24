package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;
import frc.robot.commands.Shoot;

//import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    /**Shooter routine
     * 1 Ramp up shooter, turn on AimBot
     * 2 Once RMP = target and AimBot = within range
     *      - Fire continuously
     * 3 Once button released, stop Shooter
     */

    /**
     * The Singleton instance of this Shooter. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static Shooter INSTANCE = new Shooter();

    private CANSparkMax top;
    private CANSparkMax bot;

    private DoubleSolenoid hood = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_MAIN_UP, RobotMap.SHOOTER.HOOD_MAIN_DOWN);
    private DoubleSolenoid pancake = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_LIMIT_UP, RobotMap.SHOOTER.HOOD_LIMIT_DOWN);

    /**
     * Creates a new instance of this Shooter.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private Shooter() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc

        this.top = new CANSparkMax(RobotMap.SHOOTER.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.bot = new CANSparkMax(RobotMap.SHOOTER.BOT, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.top = new CANSparkMax(12, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.bot = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.bot.setInverted(false);
        this.bot.follow(this.top);
    }

    public void initDefaultCommand(){
        setDefaultCommand(new Shoot());
    }

    public void shoot(double power){
        this.top.set(power);
        this.bot.set(power);
    }

    public void hood(boolean hood, boolean limit){
        if (hood) {
            this.hood.set(DoubleSolenoid.Value.kForward);
        } else {
            this.hood.set(DoubleSolenoid.Value.kReverse);
        }
        if (limit) {
            this.pancake.set(DoubleSolenoid.Value.kForward);
        } else {
            this.pancake.set(DoubleSolenoid.Value.kReverse);
        }
    }

    /**
     * Returns the Singleton instance of this Shooter. This static method
     * should be used -- {@code Shooter.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static Shooter getInstance() {
        return INSTANCE;
    }

}

