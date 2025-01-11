package frc.robot.subsystems.Intake.Rollers;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.units.Units;
import frc.lib.g3.MotorIOInputs;
import frc.lib.g3.SparkMaxIO;
import frc.lib.g3.UnitUtil;
import frc.robot.Constants.MotorConstants;

public class RollerIONeo550 implements RollerIO {
  private final SparkMaxIO externalRoller;
  private final SparkMaxIO internalRoller;

  public RollerIONeo550(int runExternalID, int runInternalID) {
    externalRoller = new SparkMaxIO(runExternalID, MotorType.kBrushless);
    internalRoller = new SparkMaxIO(runInternalID, MotorType.kBrushless);

    SparkMaxConfig externalConfig = new SparkMaxConfig();
    externalConfig.inverted(true);
    externalConfig.smartCurrentLimit((int) MotorConstants.CURRENT_LIMIT_550.in(Units.Amps));

    externalRoller.configure(
        externalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig internalConfig = new SparkMaxConfig();
    internalConfig.inverted(false);
    internalConfig.smartCurrentLimit((int) MotorConstants.CURRENT_LIMIT_550.in(Units.Amps));

    internalRoller.configure(
        internalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void setPower(double power) {
    externalRoller.set(power);
    internalRoller.set(power);
  }

  @Override
  public void updateInputs(RollerIOInputs inputs, MotorIOInputs external, MotorIOInputs internal) {
    externalRoller.updateInputs(external);
    internalRoller.updateInputs(internal);

    inputs.externalPosition =
        UnitUtil.reduceAngle(Units.Rotations.of(externalRoller.getEncoder().getPosition()));
    inputs.internalPosition =
        UnitUtil.reduceAngle(Units.Rotations.of(internalRoller.getEncoder().getPosition()));
  }
}
