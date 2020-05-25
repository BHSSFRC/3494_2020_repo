package frc.robot.commands.teleop;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.RunHopper;
import frc.robot.commands.RunMagazine;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Magazine;


public class RunHopperMagazine extends ParallelCommandGroup {
    public RunHopperMagazine() {
        super(new RunHopper(),
                new RunMagazine(true, true));
        /*
            Add Commands here:
            To run commands one after another, 
            use addSequential()
                e.g. addSequential(new Command1());
                     addSequential(new Command2());
                these will run in order.
            To run multiple commands at the same time,
            use addParallel()
                e.g. addParallel(new Command1());
                     addSequential(new Command2());
                Command1 and Command2 will run in parallel.
            
            A command group will require all the subsystems that each member would require.
                e.g. if Command1 requires 'chassis', and Command2 requires 'arm',
                a CommandGroup containing them would require both the chassis and the arm.
        */
    }

    @Override
    public void end(boolean interrupted) {
        Hopper.getInstance().stop();
        Magazine.getInstance().stop();
    }
}
