/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

	private Solenoid pancake;
	private Solenoid wheelOfFortune;
	private TalonSRX motor1;
	private TalonSRX motor2;

	/**
	 * Creates a new Climber.
	 */
	private final static Climber INSTANCE = new Climber();
	
	public Climber() {
		pancake = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.PANCAKE);
		wheelOfFortune = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.WHEEL_OF_FORTUNE);
		motor1 = new TalonSRX(RobotMap.CLIMBER.MOTOR1);
		motor2 = new TalonSRX(RobotMap.CLIMBER.MOTOR2);
	}

	public void pancake(boolean out) {
		pancake.set(out);
	}

	public void drive(double power) {
		motor1.set(ControlMode.PercentOutput, power);
		motor2.set(ControlMode.PercentOutput, power);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	public static Climber getInstance() {
        return INSTANCE;
	}
}
