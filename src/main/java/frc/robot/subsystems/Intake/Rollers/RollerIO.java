package frc.robot.subsystems.Intake.Rollers;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import frc.lib.g3.MotorIOInputs;

public interface RollerIO {
    @AutoLog
    public class RollerIOInputs {
        public Angle externalPosition;
        public Angle internalPosition;
    }
  
    public void setPower(double power);
  
    public void updateInputs(RollerIOInputs inputs, MotorIOInputs external, MotorIOInputs internal);
  }