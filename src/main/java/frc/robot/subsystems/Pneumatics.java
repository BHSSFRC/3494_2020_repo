/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.RunCompressor;

public class Pneumatics extends SubsystemBase {

	private Compressor pcm1;
	/**
	 * Creates a new Compressor.
	 */
	private final static Pneumatics INSTANCE = new Pneumatics();

	public Pneumatics() {
		pcm1 = new Compressor(RobotMap.COMPRESSOR.PCM1);
	}

	public void startCompressor() {
		pcm1.start();
	}

	public void stopCompressor() {
		pcm1.stop();
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	public void initDefaultCommand() {
		setDefaultCommand(new RunCompressor());
	}

	public static Pneumatics getInstance() {
		return INSTANCE;
	}
}
