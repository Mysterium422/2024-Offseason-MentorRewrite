package frc.robot.subsystems.Climber.ClimberIO;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.units.Units;
import frc.lib.g3.MotorIOInputsAutoLogged;
import frc.lib.g3.SparkMaxIO;
import frc.lib.g3.UnitUtil;
import frc.robot.subsystems.Climber.ClimberConstants;

public class ClimberIONeo implements ClimberIO {
  private final SparkMaxIO leftClimber;
  private final SparkMaxIO rightClimber;

  public ClimberIONeo(int leftClimberID, int rightClimberID) {
    leftClimber = new SparkMaxIO(leftClimberID, MotorType.kBrushless);
    rightClimber = new SparkMaxIO(rightClimberID, MotorType.kBrushless);

    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(1);

    leftClimber.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightClimber.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    leftClimber.getEncoder().setPosition(0);
    rightClimber.getEncoder().setPosition(0);
  }

  public void setPower(double leftPower, double rightPower) {
    leftClimber.set(leftPower);
    rightClimber.set(rightPower);
  }

  public void updateInputs(ClimberIOInputs inputs, MotorIOInputsAutoLogged left, MotorIOInputsAutoLogged right) {
    inputs.leftPosition = UnitUtil.angleToDistance(Units.Rotations.of(leftClimber.getEncoder().getPosition()), ClimberConstants.pulleyRadius).divide(ClimberConstants.gearing);
    inputs.rightPosition = UnitUtil.angleToDistance(Units.Rotations.of(rightClimber.getEncoder().getPosition()), ClimberConstants.pulleyRadius).divide(ClimberConstants.gearing);
    leftClimber.updateInputs(left);
    rightClimber.updateInputs(right);
  }
}
