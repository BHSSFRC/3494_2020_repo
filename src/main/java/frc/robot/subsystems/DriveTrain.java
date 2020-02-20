package frc.robot.subsystems;

import com.ctre.phoenix.music.Orchestra;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.commands.Music;

public class DriveTrain extends SubsystemBase {
    private TalonFX leftMaster;
    private TalonFX leftSlave;
    private TalonFX rightMaster;
    private TalonFX rightSlave;
    private Orchestra orchestra;

    private final static DriveTrain INSTANCE = new DriveTrain();

    private DriveTrain() {
        TalonFX [] motors =  {
            new TalonFX(RobotMap.DRIVETRAIN.LEFT_MASTER),
            new TalonFX(RobotMap.DRIVETRAIN.LEFT_SLAVE),
            new TalonFX(RobotMap.DRIVETRAIN.RIGHT_MASTER),
            new TalonFX(RobotMap.DRIVETRAIN.RIGHT_SLAVE)
        };

        ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();
      
        for (int i = 0; i < motors.length; ++i) {
            instruments.add(motors[i]);
        }

        orchestra = new Orchestra(instruments);
        orchestra.loadMusic("mbk.chrp");
    }

    public void beepBoop() {
        if (!orchestra.isPlaying()) {
            orchestra.play();
        }
    }

    public void initDefaultCommand(){
        setDefaultCommand(new Music());
    }

    public void stop(){

    }

    public static DriveTrain getInstance() {
        return INSTANCE;
    }

}

