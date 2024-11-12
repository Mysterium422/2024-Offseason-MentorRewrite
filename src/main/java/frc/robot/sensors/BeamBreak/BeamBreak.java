package frc.robot.sensors.BeamBreak;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.VirtualSubsystem;
import frc.robot.sensors.BeamBreak.BeamBreakIO.BeamBreakIO;
import frc.robot.sensors.BeamBreak.BeamBreakIO.BeamBreakIOInputsAutoLogged;

public class BeamBreak extends VirtualSubsystem {
  private BeamBreakIO beamBreakIO;
  private BeamBreakIOInputsAutoLogged inputs = new BeamBreakIOInputsAutoLogged();
  private double initialBreakTimestamp;
  private boolean brokenLastCycle;

  public BeamBreak(BeamBreakIO beamBreakIO) {
    this.beamBreakIO = beamBreakIO;
  }

  public boolean isBroken() {
    return inputs.isBroken && brokenLastCycle;
  }

  public double getBrokenTime() {
    if (isBroken()) {
      return Timer.getFPGATimestamp() - initialBreakTimestamp;
    } else {
      return 0.0;
    }
  }

  @Override
  public void periodic() {
    beamBreakIO.updateInputs(inputs);
    Logger.processInputs(getName(), inputs);

    if (inputs.isBroken && !brokenLastCycle) {
      initialBreakTimestamp = Timer.getFPGATimestamp();
      brokenLastCycle = true;
    } else if (!inputs.isBroken) {
      brokenLastCycle = false;
    }
    
    Logger.recordOutput(getName() + "/brokenTwice", isBroken());
    Logger.recordOutput(getName() + "/brokenTime", getBrokenTime());
  }
}
