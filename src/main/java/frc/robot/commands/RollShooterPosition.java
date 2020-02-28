/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.fasterxml.jackson.databind.ser.std.EnumSerializer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RollShooterPosition extends InstantCommand {

  boolean dir;
  public RollShooterPosition(boolean dir){
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    this.dir = dir;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shooter.Position pos = Shooter.getInstance().getPosition();

      if(this.dir) { // forward
        if (pos != Shooter.Position.THREE)
          Shooter.getInstance().setPosition(pos.next());
      } 
      else { // backward
        if (pos != Shooter.Position.ONE)
          Shooter.getInstance().setPosition(pos.prev());
      }


  }
}
