// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.sql.Driver;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Constants.Mode;
import frc.robot.Ports.ClimberPorts;
import frc.robot.oi.Controls;
import frc.robot.oi.OneDriverControlsImpl;
import frc.robot.oi.TwoDriverControlsImpl;
import frc.robot.subsystems.Climber.Climber;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIO;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIONeo;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIOSim;

public class RobotContainer {
  private Controls m_controls = new OneDriverControlsImpl(0);
  private Climber m_climber;

  public RobotContainer() {
    // refreshControllers();
    m_climber = buildClimber(Constants.getMode());
    configureBindings();
  }

  private void refreshControllers() {
    ArrayList<Integer> joysticksConnected = new ArrayList<>();
    for (int i = 0; i < DriverStation.kJoystickPorts; i++) {
      if (DriverStation.isJoystickConnected(i)) {
        joysticksConnected.add(i);
      }
    }
    
    if (joysticksConnected.size() >= 2) {
      m_controls = new TwoDriverControlsImpl(joysticksConnected.get(0), joysticksConnected.get(1));
    } else if (joysticksConnected.size() == 1) {
      DriverStation.reportWarning("[G3] Only one controller connected. Implementing One Driver Controls. (" + joysticksConnected.get(0) + ")", true);
      m_controls = new OneDriverControlsImpl(joysticksConnected.get(0));
    } else {
      DriverStation.reportWarning("[G3] No controllers detected. Implement default controls.", true);
      m_controls = new TwoDriverControlsImpl(0, 1);
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
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  private Climber buildClimber(Mode mode) {
    DriverStation.reportWarning(mode.toString(), false);
    switch (mode) {
      case SIM:
        DriverStation.reportWarning(mode.toString(), false);
        return new Climber(new ClimberIOSim());
      case REPLAY:
        return new Climber(new ClimberIO() {});
      default:
      DriverStation.reportWarning(mode.toString() + "2", false);
        return new Climber(
            new ClimberIONeo(ClimberPorts.leftClimberID, ClimberPorts.rightClimberID));
    }
  }
}
