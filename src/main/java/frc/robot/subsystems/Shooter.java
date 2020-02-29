package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    //TODO: get method for whether shooter is at target speed yet
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
        
        Position(DoubleSolenoid.Value hood, DoubleSolenoid.Value limiter){
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

        this.left.setInverted(true);
        this.right.setInverted(true);

        this.setPosition(Position.ONE);
    }

    public void shoot(double power) {
        this.left.set(power);
        this.right.set(-power);
        SmartDashboard.putNumber("Shooter Power Current", power);
    }

    public boolean atTargetSpeed(double targetRPM) {
        return Math.abs(this.getRPM() - targetRPM) < 10;
    }

    public double getRPM() {
        return (this.left.getEncoder().getVelocity()
                + this.right.getEncoder().getVelocity()) / 2;
    }

    public void stop()
    {
        //this.left.set(0);
        this.right.set(0);
    }

    public Position getPosition()
    {
        return this.currentPosition;
    }

    /*
    public double getVelocity() {
        return ((this.left.getVelocity() + this.left.getVelocity()) / 2);
    }
    */

    public void setPosition(Position position) {
        // hood = "long piston"
        // limiter = "pancake"
        if (position != this.currentPosition) {
            if (this.currentPosition == Position.ONE) {
                switch(position){
                    case TWO:
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        break;
                    case THREE:
                        this.limiter.set(DoubleSolenoid.Value.kForward);
                        Timer.delay(50E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            } else if (this.currentPosition == Position.TWO) {
                switch(position){
                    case ONE:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        break;
                    case THREE:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(50E-3);
                        this.limiter.set(DoubleSolenoid.Value.kForward);
                        Timer.delay(50E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            } else if (this.currentPosition == Position.THREE) {
                switch(position){
                    case ONE:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        break;
                    case TWO:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(100E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            }

            this.currentPosition = position;
        }

    }

    public static Shooter getInstance() {
        return INSTANCE;
    }

}

