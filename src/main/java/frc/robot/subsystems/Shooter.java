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
        ONE(DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse), 
        TWO(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kReverse), 
        THREE(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kForward);

        private final DoubleSolenoid.Value hood, limiter;
        
        private Position(DoubleSolenoid.Value hood, DoubleSolenoid.Value limiter){
            this.hood = hood; // "long piston"
            this.limiter = limiter; // "pancake piston"
        }

        public DoubleSolenoid.Value getHood() {
            return this.hood;
        }

        public DoubleSolenoid.Value getLimiter() {
            return this.limiter;
        }

        public Position prev() {
            if (this == Position.ONE) {
                return Position.THREE;
            }
            else if (this == Position.TWO) {
                return Position.ONE;
            }
            else if (this == Position.THREE) {
                return Position.TWO;
            }
            else
            {
                return Position.ONE;
            }
        }

        public Position next()
        {
            if (this == Position.ONE) {
                return Position.TWO;
            }
            else if (this == Position.TWO) {
                return Position.THREE;
            }
            else if (this == Position.THREE) {
                return Position.ONE;
            }
            else
            {
                return Position.ONE;
            }
        }
    }

    private Position currentPosition;

    private Shooter() {
        this.left = new CANSparkMax(RobotMap.SHOOTER.LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.right= new CANSparkMax(RobotMap.SHOOTER.RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);

        this.setPosition(Position.ONE);
    }

    public void shoot(double power) {
        this.left.set(power);
        this.right.set(power);
    }

    public Position getPosition()
    {
        return this.currentPosition;
    }

    public double getVelocity() {
        return ((this.left.getVelocity() + this.left.getVelocity()) / 2);
    }

    public void setPosition(Position position) {
        if (position != this.currentPosition){
            if (position != Position.THREE) {
                this.hood.set(position.getHood());
                this.limiter.set(position.getLimiter());
            }
            else
            {
                this.setPosition(Position.ONE);
                this.hood.set(position.getHood());
                Timer.delay(50E-3); // 50ms
                this.limiter.set(position.getLimiter());
            }
        }
        
        this.currentPosition = position;
    }

    public static Shooter getInstance() {
        return INSTANCE;
    }

}

