package frc.robot.sensors.BeamBreak.BeamBreakIO;

import org.littletonrobotics.junction.AutoLog;

public interface BeamBreakIO {
  @AutoLog
  public class BeamBreakIOInputs {
    public boolean isBroken = false;
  }

  public default void updateInputs(BeamBreakIOInputs inputs) {}
}
