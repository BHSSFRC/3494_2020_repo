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

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

	private Solenoid climberSolenoid;
	private Solenoid wheelOfFortune;
	private TalonSRX left;
	private TalonSRX right;

	private final static Climber INSTANCE = new Climber();
	
	public Climber() {
		this.climberSolenoid = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.CLIMBER_SOLENOID);
		this.wheelOfFortune = new Solenoid(RobotMap.COMPRESSOR.PCM1, RobotMap.CLIMBER.WHEEL_OF_FORTUNE);
		this.left = new TalonSRX(RobotMap.CLIMBER.LEFT);
		this.right = new TalonSRX(RobotMap.CLIMBER.RIGHT);

		left.configClosedloopRamp(RobotMap.CLIMBER.CLIMB_UP_RAMPRATE);
		right.configClosedloopRamp(RobotMap.CLIMBER.CLIMB_UP_RAMPRATE);
		this.resetEncoder();
	}

	public void spin(double power) {
		this.left.set(ControlMode.PercentOutput, power);
		this.right.set(ControlMode.PercentOutput, power);
	}

	public void extendClimber(boolean out) {
		this.climberSolenoid.set(out);
	}

	public double getEncoderPosition(){
		return this.left.getSensorCollection().getQuadraturePosition();
	}

	public double getEncoderPositionInches(){
		return this.getEncoderPosition() * RobotMap.CLIMBER.INCHES_PER_ENCODER_ROTATION;
	}

	public void resetEncoder(){
		this.left.getSensorCollection().setQuadraturePosition(0, 10);
	}

	public void drive(double power) {
		right.set(ControlMode.PercentOutput, power);
		left.set(ControlMode.PercentOutput, power);
	}

	public void stop() {
		right.set(ControlMode.PercentOutput, 0);
		left.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void periodic() {
	}

	public static Climber getInstance() {
        return INSTANCE;
	}
}
