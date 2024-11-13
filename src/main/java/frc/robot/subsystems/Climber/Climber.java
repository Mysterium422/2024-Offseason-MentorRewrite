package frc.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.RobotTime;
import frc.lib.g3.MotorIOInputsAutoLogged;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIO;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIOInputsAutoLogged;
import org.littletonrobotics.junction.Logger;

public class Climber extends SubsystemBase {

  private ClimberIO climberIO;
  private ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();
  private MotorIOInputsAutoLogged leftInputs = new MotorIOInputsAutoLogged();
  private MotorIOInputsAutoLogged rightInputs = new MotorIOInputsAutoLogged();
  private double leftPowerDesired;
  private double rightPowerDesired;
  private Mechanism2d viz = new Mechanism2d(4, 10);
  private MechanismLigament2d vizLeft =
      viz.getRoot("LeftClimber", 1, 0).append(new MechanismLigament2d("LeftHeight", 0, 0));
  private MechanismLigament2d vizRight =
      viz.getRoot("RightClimber", 3, 0).append(new MechanismLigament2d("RightHeight", 0, 0));

  public Climber(ClimberIO climberIO) {
    super("Climber");
    this.climberIO = climberIO;
  }

  @Override
  public void periodic() {
    double timestamp = RobotTime.getTimestampSeconds();
    climberIO.updateInputs(inputs, leftInputs, rightInputs);
    Logger.processInputs(getName(), inputs);
    Logger.processInputs(getName() + "/left", leftInputs);
    Logger.processInputs(getName() + "/right", rightInputs);

    vizLeft.setLength(inputs.leftPosition / 15);
    vizRight.setLength(inputs.rightPosition / 15);

    Logger.recordOutput(getName() + "/leftPowerDesired", leftPowerDesired);
    Logger.recordOutput(getName() + "/rightPowerDesired", rightPowerDesired);
    Logger.recordOutput(getName() + "/viz", viz);
    Logger.recordOutput(
        getName() + "/latencyPeriodicSec", RobotTime.getTimestampSeconds() - timestamp);
  }

  public void setPower(double leftPower, double rightPower) {
    climberIO.setPower(leftPower, rightPower);
    leftPowerDesired = leftPower;
    rightPowerDesired = rightPower;
  }
}
