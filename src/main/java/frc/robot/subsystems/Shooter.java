package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    /**
     * Shooter routine
     * 1 Ramp up shooter, turn on AimBot
     * 2 Once RMP = target and AimBot = within range
     * - Fire continuously
     * 3 Once button released, stop Shooter
     */

    private final static Shooter INSTANCE = new Shooter();

    private CANSparkMax top;
    private CANSparkMax bot;

    private DoubleSolenoid hood = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_MAIN_UP, RobotMap.SHOOTER.HOOD_MAIN_DOWN);
    private DoubleSolenoid limiter = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_LIMIT_UP, RobotMap.SHOOTER.HOOD_LIMIT_DOWN);


    private Shooter() {
        this.top = new CANSparkMax(RobotMap.SHOOTER.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.bot = new CANSparkMax(RobotMap.SHOOTER.BOT, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void shoot(double power) {
        this.top.set(power);
        this.bot.set(power);
    }

    public void hood(boolean hood, boolean limit) {
        if (hood) {
            this.hood.set(DoubleSolenoid.Value.kForward);
        } else {
            this.hood.set(DoubleSolenoid.Value.kReverse);
        }
        if (limit) {
            this.limiter.set(DoubleSolenoid.Value.kForward);
        } else {
            this.limiter.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public static Shooter getInstance() {
        return INSTANCE;
    }

}

