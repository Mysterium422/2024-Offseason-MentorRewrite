package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.g3.MotorIOInputsAutoLogged;
import frc.robot.sensors.BeamBreak.BeamBreak;
import frc.robot.subsystems.Intake.Rollers.RollerIO;
import frc.robot.subsystems.Intake.Rollers.RollerIOInputsAutoLogged;

public class Intake extends SubsystemBase {
    private RollerIO rollerIO;
    private RollerIOInputsAutoLogged rollerIOInputs = new RollerIOInputsAutoLogged();
    private MotorIOInputsAutoLogged externalIOInputs = new MotorIOInputsAutoLogged();
    private MotorIOInputsAutoLogged internalIOInputs = new MotorIOInputsAutoLogged();

    private BeamBreak beamBreak;

    public enum IntakeState {
        INTAKING,
        VOMITING,
        TUNING,
        IDLE,
        NOTE_HELD,
        SHOOTING
    }

    private IntakeState currentState = IntakeState.IDLE;

    public Intake(RollerIO rollerIO, BeamBreak beamBreak) {
        super("Intake");
        this.rollerIO = rollerIO;
        this.beamBreak = beamBreak;
    }

    public void setState(IntakeState newState) {
        currentState = newState;
    }

    @Override
    public void periodic() {
        rollerIO.updateInputs(rollerIOInputs, externalIOInputs, internalIOInputs);
        Logger.processInputs(getName(), rollerIOInputs);
        Logger.processInputs(getName() + "/external", externalIOInputs);
        Logger.processInputs(getName() + "/internal", internalIOInputs);

        double desiredPower;

        switch(currentState) {
            case INTAKING:
                desiredPower = 1;
                if (beamBreak.isBroken()) {
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

        Logger.recordOutput(getName() + "/state", currentState);
    }

    
}
