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

  private int pov;
  public RollShooterPosition(int pov){
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    this.pov = pov;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Shooter.Position pos = Shooter.getInstance().getPosition();

    if (this.pov == 90 || this.pov == 180){
      if(pov == 90) { // forward
        if (pos != Shooter.Position.THREE)
          Shooter.getInstance().setPosition(pos.next());
      } 
      else if (pov == 180) {
        if (pos != Shooter.Position.ONE)
          Shooter.getInstance().setPosition(pos.prev());
      }
    }


  }
}
