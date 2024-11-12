package frc.lib;

import java.util.ArrayList;
import java.util.List;

import org.littletonrobotics.junction.Logger;

import lombok.Getter;
import lombok.Setter;

public abstract class VirtualSubsystem {
  private static List<VirtualSubsystem> subsystems = new ArrayList<>();
  @Getter @Setter private String name;

  public VirtualSubsystem() {
    this(null);
    String name = this.getClass().getSimpleName();
    this.name = name.substring(name.lastIndexOf('.') + 1);
  }

  public VirtualSubsystem(String name) {
    subsystems.add(this);
    if (name == null) {
      name = this.getClass().getSimpleName();
      name = name.substring(name.lastIndexOf('.') + 1);
    }
    this.name = name;
  }

  /** Calls {@link #periodic()} on all virtual subsystems. */
  public static void periodicAll() {
    for (var subsystem : subsystems) {
      double timestamp = RobotTime.getTimestampSeconds();
      subsystem.periodic();
      Logger.recordOutput(subsystem.getName() + "/latencyPeriodicSec", RobotTime.getTimestampSeconds() - timestamp);
    }
  }

  /** This method is called periodically once per loop cycle. */
  public abstract void periodic();
}
