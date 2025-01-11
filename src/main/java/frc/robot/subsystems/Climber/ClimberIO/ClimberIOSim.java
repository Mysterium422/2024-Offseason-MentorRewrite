package frc.robot.subsystems.Climber.ClimberIO;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import frc.lib.g3.FrictionMotorSim;
import frc.lib.g3.MotorIOInputsAutoLogged;
import frc.lib.g3.UnitUtil;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;
import frc.robot.subsystems.Climber.ClimberConstants;

public class ClimberIOSim implements ClimberIO {
  private final FrictionMotorSim leftMotorSim =
      new FrictionMotorSim(DCMotor.getNEO(1), Units.KilogramSquareMeters.of(0.004), true);
  private final FrictionMotorSim rightMotorSim =
      new FrictionMotorSim(DCMotor.getNEO(1), Units.KilogramSquareMeters.of(0.004), true);

  @Override
  public void setPower(double leftPower, double rightPower) {
    leftMotorSim.set(leftPower);
    rightMotorSim.set(rightPower);

    leftMotorSim.smartCurrentLimit(MotorConstants.CURRENT_LIMIT_1650);
    rightMotorSim.smartCurrentLimit(MotorConstants.CURRENT_LIMIT_1650);
  }

  @Override
  public void updateInputs(
      ClimberIOInputs inputs, MotorIOInputsAutoLogged left, MotorIOInputsAutoLogged right) {
    leftMotorSim.update(Constants.loopTime);
    rightMotorSim.update(Constants.loopTime);

    leftMotorSim.updateInputs(left);
    rightMotorSim.updateInputs(right);

    inputs.leftPosition =
        UnitUtil.angleToDistance(leftMotorSim.getPosition(), ClimberConstants.pulleyRadius)
            .divide(ClimberConstants.gearing);
    inputs.rightPosition =
        UnitUtil.angleToDistance(rightMotorSim.getPosition(), ClimberConstants.pulleyRadius)
            .divide(ClimberConstants.gearing);
  }
}
