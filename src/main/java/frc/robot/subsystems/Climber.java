/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

	private Solenoid climberSolenoid;
	private Solenoid wheelOfFortune;
	private TalonSRX left;
	private TalonSRX right;

	/**
	 * Creates a new Climber.
	 */
	private final static Climber INSTANCE = new Climber();
	
	public Climber() {
		this.climberSolenoid = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.CLIMBER_SOLENOID);
		this.wheelOfFortune = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.WHEEL_OF_FORTUNE);
		this.left = new TalonSRX(RobotMap.CLIMBER.LEFT);
		this.right = new TalonSRX(RobotMap.CLIMBER.RIGHT);
	}

	public void spin(double power) {
		this.left.set(ControlMode.PercentOutput, power);
		this.right.set(ControlMode.PercentOutput, power);
	}

	public void extendClimber(boolean out) {
		this.climberSolenoid.set(out);
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	public static Climber getInstance() {
        return INSTANCE;
	}
}
