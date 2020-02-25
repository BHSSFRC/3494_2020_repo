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

public class Climb extends CommandBase {
  public Climb() {
      addRequirements(Climber.getInstance());
  }

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		Climber.getInstance().pancake(OI.getINSTANCE().getXboxLeftBumper());
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
