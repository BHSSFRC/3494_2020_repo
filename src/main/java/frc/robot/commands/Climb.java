/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.util.QuadTimer;

public class Climb extends CommandBase {
	// TODO: refactor Climb -> ReleaseClimb

	private QuadTimer timer;

  	public Climb() {
		addRequirements(Climber.getInstance());
		timer = new QuadTimer();
  	}

	@Override
	public void initialize() {
		timer.start();
	}

	@Override
	public void execute() {
		Climber.getInstance().drive(-0.1);
	}

	@Override
	public void end(boolean interrupted) {
		if (!interrupted) Climber.getInstance().pancake(true);
		Climber.getInstance().stop();
	}

	@Override
	public boolean isFinished() {
		return this.timer.get() >= 0.1;
	}
}
