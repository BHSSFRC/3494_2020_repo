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
     * 2 Once RMP = target and Aimright= within range
     * - Fire continuously
     * 3 Once button released, sleft Shooter
     */

    private final static Shooter INSTANCE = new Shooter();

    private CANSparkMax left;
    private CANSparkMax right;

    private DoubleSolenoid hood = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_MAIN_UP, RobotMap.SHOOTER.HOOD_MAIN_DOWN);
    private DoubleSolenoid limiter = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_LIMIT_UP, RobotMap.SHOOTER.HOOD_LIMIT_DOWN);

    public enum Position
    {
        ONE(false, false), TWO(false, false), THREE(false, false);

        private final boolean pancake, limiter;
        
        private Position(boolean pancake, boolean limiter){
            this.pancake = pancake;
            this.limiter = limiter;
        }

        public boolean getPancake() {
            return this.pancake;
        }

        public boolean getLimiter() {
            return this.limiter;
        }
    }

    private Shooter() {
        this.left = new CANSparkMax(RobotMap.SHOOTER.LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.right= new CANSparkMax(RobotMap.SHOOTER.RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void shoot(double power) {
        this.left.set(power);
        this.right.set(power);
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

