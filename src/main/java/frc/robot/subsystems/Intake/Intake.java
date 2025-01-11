package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.g3.MotorIOInputsAutoLogged;
import frc.robot.sensors.BeamBreak.BeamBreak;
import frc.robot.subsystems.Intake.Rollers.RollerIO;
import frc.robot.subsystems.Intake.Rollers.RollerIOInputsAutoLogged;
import lombok.Getter;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  private final RollerIO rollerIO;
  private final RollerIOInputsAutoLogged rollerIOInputs = new RollerIOInputsAutoLogged();
  private final MotorIOInputsAutoLogged externalIOInputs = new MotorIOInputsAutoLogged();
  private final MotorIOInputsAutoLogged internalIOInputs = new MotorIOInputsAutoLogged();
  private final IntakeViz viz = new IntakeViz();

  private BeamBreak beamBreak;

  public enum IntakeState {
    INTAKING,
    VOMITING,
    TUNING,
    IDLE,
    NOTE_HELD,
    SHOOTING
  }

  @Getter private IntakeState state = IntakeState.IDLE;

  public Intake(RollerIO rollerIO, BeamBreak beamBreak) {
    super("Intake");
    this.rollerIO = rollerIO;
    this.beamBreak = beamBreak;
  }

  public void setState(IntakeState newState) {
    state = newState;
  }

  @Override
  public void periodic() {
    rollerIO.updateInputs(rollerIOInputs, externalIOInputs, internalIOInputs);
    Logger.processInputs(getName(), rollerIOInputs);
    Logger.processInputs(getName() + "/external", externalIOInputs);
    Logger.processInputs(getName() + "/internal", internalIOInputs);

    double desiredPower;

    switch (state) {
      case INTAKING:
        desiredPower = 1;
        if (beamBreak.isBroken()) {
          desiredPower = 0;
          setState(IntakeState.NOTE_HELD);
        }
        break;
      case SHOOTING:
        desiredPower = 1;
        break;
      case VOMITING:
        desiredPower = -1;
        break;
      case TUNING:
        desiredPower = 0;
        break;
      case NOTE_HELD:
      case IDLE:
      default:
        desiredPower = 0;
        break;
    }

    rollerIO.setPower(desiredPower);

    Logger.recordOutput(getName() + "/state", state);
    Logger.recordOutput(getName() + "/desiredPower", desiredPower);
    viz.updateViz(rollerIOInputs);
    Logger.recordOutput(getName() + "/viz", viz.getViz());
  }
}
