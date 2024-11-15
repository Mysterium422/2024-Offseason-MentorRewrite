// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Constants.Mode;
import frc.robot.oi.Controls;
import frc.robot.oi.OneDriverControlsImpl;
import frc.robot.oi.TwoDriverControlsImpl;
import frc.robot.sensors.BeamBreak.BeamBreak;
import frc.robot.sensors.BeamBreak.BeamBreakIO.BeamBreakIO;
import frc.robot.sensors.BeamBreak.BeamBreakIO.BeamBreakIODIO;
import frc.robot.sensors.BeamBreak.BeamBreakIO.BeamBreakIOSim;
import frc.robot.subsystems.Climber.Climber;
import frc.robot.subsystems.Climber.ClimberConstants;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIO;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIONeo;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIOSim;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.Intake.IntakeState;
import frc.robot.subsystems.Intake.IntakeConstants;
import frc.robot.subsystems.Intake.Rollers.RollerIO;
import frc.robot.subsystems.Intake.Rollers.RollerIONeo550;
import frc.robot.subsystems.Intake.Rollers.RollerIOSim;

public class RobotContainer {
  private final Controls m_controls = new OneDriverControlsImpl(0);
  private final Climber m_climber;
  private final BeamBreak m_beamBreak;
  private final Intake m_intake;

  public RobotContainer() {
    Mode mode = Constants.getMode();
    m_climber = buildClimber(mode);
    m_beamBreak = buildBeamBreak(mode);
    m_intake = buildIntake(mode, m_beamBreak);
    configureBindings();
  }

  private Controls refreshControllers() {
    ArrayList<Integer> joysticksConnected = new ArrayList<>();
    for (int i = 0; i < DriverStation.kJoystickPorts; i++) {
      if (DriverStation.isJoystickConnected(i)) {
        joysticksConnected.add(i);
      }
    }
    
    if (joysticksConnected.size() >= 2) {
      return new TwoDriverControlsImpl(joysticksConnected.get(0), joysticksConnected.get(1));
    } else if (joysticksConnected.size() == 1) {
      DriverStation.reportWarning("[G3] Only one controller connected. Implementing One Driver Controls. (" + joysticksConnected.get(0) + ")", true);
      return new OneDriverControlsImpl(joysticksConnected.get(0));
    } else {
      DriverStation.reportWarning("[G3] No controllers detected. Implement default controls.", true);
      return new TwoDriverControlsImpl(0, 1);
    }
  }

  private void configureBindings() {

    m_climber.setDefaultCommand(
        new RunCommand(
            () -> {
              m_climber.setPower(m_controls.getClimbLeftOnly(), m_controls.getClimbRightOnly());
            }, 
            m_climber));

    m_controls
        .climbUp()
        .whileTrue(
            new StartEndCommand(
                () -> m_climber.setPower(1, 1), () -> m_climber.setPower(0, 0), m_climber));

    m_controls
        .climbDown()
        .whileTrue(
            new StartEndCommand(
                () -> m_climber.setPower(-1, -1), () -> m_climber.setPower(0, 0), m_climber));

    m_controls
        .intake()
        .whileTrue(
            new StartEndCommand(
                () -> {
                  if (!m_beamBreak.isBroken()) {
                    m_intake.setState(IntakeState.INTAKING);
                  }
                },
                () -> {
                  if (m_intake.getState() != IntakeState.NOTE_HELD) {
                    m_intake.setState(IntakeState.IDLE);
                  }
                },
                m_intake));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  private Climber buildClimber(Mode mode) {
    return switch(mode) {
      case SIM -> new Climber(new ClimberIOSim());
      case REPLAY -> new Climber(new ClimberIO() {});
      default -> new Climber(new ClimberIONeo(ClimberConstants.portLeftClimberID, ClimberConstants.portRightClimberID));
    };
  }

  private BeamBreak buildBeamBreak(Mode mode) {
    return switch (mode) {
      case SIM -> new BeamBreak(new BeamBreakIOSim());
      case REPLAY -> new BeamBreak(new BeamBreakIO() {});
      default -> new BeamBreak(new BeamBreakIODIO(IntakeConstants.portBeamBreakID));
    };
  }

  private Intake buildIntake(Mode mode, BeamBreak beamBreak) {
    return switch (mode) {
      case SIM -> new Intake(new RollerIOSim(), beamBreak);
      case REPLAY -> new Intake(new RollerIO() {}, beamBreak);
      default -> new Intake(new RollerIONeo550(IntakeConstants.portExternalMotorID, IntakeConstants.portInternalMotorID), beamBreak);
    };
  }

  // private Intake buildIntake(Mode mode) {
  //   switch (mode) {
  //     case SIM:
  //       return new Intake
  //   }
  // }
}
