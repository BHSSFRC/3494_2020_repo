package frc.robot.subsystems;


import com.revrobotics.*;
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
    private CANEncoder leftEnc;
    private CANPIDController leftPID;

    private CANSparkMax right;
    private CANEncoder rightEnc;
    private CANPIDController rightPID;

    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    private DoubleSolenoid hood = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_MAIN_UP, RobotMap.SHOOTER.HOOD_MAIN_DOWN);
    private DoubleSolenoid limiter = new DoubleSolenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.SHOOTER.HOOD_LIMIT_UP, RobotMap.SHOOTER.HOOD_LIMIT_DOWN);

    public enum Position
    {
        ONE(DoubleSolenoid.Value.kReverse, DoubleSolenoid.Value.kReverse), 
        TWO(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kForward),
        THREE(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kReverse);

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
        this.left.setOpenLoopRampRate(0.4);
        this.right.setOpenLoopRampRate(0.4);
        this.left.setClosedLoopRampRate(0.4);
        this.right.setClosedLoopRampRate(0.4);

        this.left.setInverted(true);
        this.right.setInverted(true);

        this.leftEnc = this.left.getEncoder();
        this.leftPID = this.left.getPIDController();

        this.rightEnc = this.right.getEncoder();
        this.rightPID = this.right.getPIDController();

        kP = 0.00008;
        kI = 0.000000;//0.000001;
        kD = 0.00032;
        kIz = 0.0;
        kFF = 0.000175;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        leftPID.setP(kP);
        leftPID.setI(kI);
        leftPID.setD(kD);
        leftPID.setIZone(kIz);
        leftPID.setFF(kFF);
        leftPID.setOutputRange(kMinOutput, kMaxOutput);

        rightPID.setP(kP);
        rightPID.setI(kI);
        rightPID.setD(kD);
        rightPID.setIZone(kIz);
        rightPID.setFF(kFF);
        rightPID.setOutputRange(kMinOutput, kMaxOutput);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        this.setPosition(Position.ONE);
    }

    public void shoot(double power) {
        this.left.set(power);
        this.right.set(-power);
        SmartDashboard.putNumber("Shooter Power Current", power);
    }

    public void setRPM(double targetRPM) {
        //this.left.setRef
        //targetRPM /= .2743;
        this.leftPID.setReference(targetRPM, ControlType.kVelocity);
        this.rightPID.setReference(-targetRPM, ControlType.kVelocity);
        //System.out.println("RPM: " + this.getRPM() + "Target: " + targetRPM + " FF: " + this.leftPID.getFF());
    }

    public boolean atTargetSpeed(double targetRPM) {
        return Math.abs(this.getRPM() - targetRPM) < 10;
    }

    public double getRPM() {
        return (Math.abs(this.getLeftRPM()) + Math.abs(this.getRightRPM())) / 2;
    }

    public double getLeftRPM() {
        //return this.leftEnc.getPosition();
        return this.leftEnc.getVelocity();
    }
    public double getRightRPM() {
        return this.rightEnc.getVelocity();
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

        System.out.println("Current Pos: " + this.currentPosition + " Goal Pos: " + position);
        if (position != this.currentPosition) {
            this.limiter.set(DoubleSolenoid.Value.kReverse);
            this.hood.set(DoubleSolenoid.Value.kReverse);
            Timer.delay(500E-3);

            switch(position){
                case TWO:
                    this.limiter.set(DoubleSolenoid.Value.kForward);
                    Timer.delay(500E-3);
                    this.hood.set(DoubleSolenoid.Value.kForward);
                    break;
                case THREE:
                    this.hood.set(DoubleSolenoid.Value.kForward);
                    break;
            }
            //System.out.println("if 1");
            /*if (this.currentPosition == Position.ONE) {
                switch(position){
                    case TWO:
                        System.out.println("1 to 2");
                        this.limiter.set(DoubleSolenoid.Value.kForward);
                        Timer.delay(500E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                    case THREE:
                        System.out.println("1 to 3");
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            } else if (this.currentPosition == Position.TWO) {
                //System.out.println("if 2");
                switch(position){
                    case ONE:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        break;
                    case THREE:
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        //Timer.delay(500E-3);
                        //this.limiter.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            } else if (this.currentPosition == Position.THREE) {
                System.out.println("if 3");
                switch(position){
                    case ONE:
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        break;
                    case TWO:
                        this.limiter.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.hood.set(DoubleSolenoid.Value.kReverse);
                        Timer.delay(500E-3);
                        this.limiter.set(DoubleSolenoid.Value.kForward);
                        Timer.delay(500E-3);
                        this.hood.set(DoubleSolenoid.Value.kForward);
                        break;
                }
            }*/

            this.currentPosition = position;
        }

    }

    public void updateMotorPID(){
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { leftPID.setP(p); kP = p; rightPID.setP(p);}
        if((i != kI)) { leftPID.setI(i); kI = i; rightPID.setI(i);}
        if((d != kD)) { leftPID.setD(d); kD = d; rightPID.setD(d);}
        if((iz != kIz)) { leftPID.setIZone(iz); kIz = iz; rightPID.setIZone(iz);}
        if((ff != kFF)) { leftPID.setFF(ff); kFF = ff; rightPID.setFF(ff);}
        if((max != kMaxOutput) || (min != kMinOutput)) {
            leftPID.setOutputRange(min, max);
            rightPID.setOutputRange(min, max);
            kMinOutput = min; kMaxOutput = max;
        }
    }

    public static Shooter getInstance() {
        return INSTANCE;
    }

}

