/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.RunIntake;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
  	/**
  	 * Creates a new Intake.
  	 */

    private TalonSRX motor;


    private final static Intake INSTANCE = new Intake();

  	public Intake() {
  	    this.motor = new TalonSRX(RobotMap.INTAKE.MOTOR);
  	}

    public void initDefaultCommand(){
        setDefaultCommand(new RunIntake());
    }

    public void runIntake(double power) {
        this.motor.set(ControlMode.PercentOutput, power);
    }
	
  	@Override
  	public void periodic() {
  	  	// This method will be called once per scheduler run
  	}

    public static Intake getInstance() {
        return INSTANCE;
    }
}
