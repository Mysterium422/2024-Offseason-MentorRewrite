package frc.robot.oi;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.DoublePressTracker;

public class CommandXboxControllerOI extends CommandXboxController {
  public double currentRumbleValue;

  public CommandXboxControllerOI(int port) {
    super(port);
  }

  public void setRumble(RumbleType rumbleType, double value) {
    if (currentRumbleValue != value) {
      super.setRumble(rumbleType, value);
      currentRumbleValue = value;
    }
  }

  public Trigger doublePress(Trigger base) {
    return DoublePressTracker.doublePress(base);
  }

  public Trigger holdPress(Trigger base) {
    return base.debounce(1);
  }
}
