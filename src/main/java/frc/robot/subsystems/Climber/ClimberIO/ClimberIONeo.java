package frc.robot.subsystems.Climber.ClimberIO;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.lib.g3.SparkMaxIO;

public class ClimberIONeo implements ClimberIO {
    private SparkMaxIO rightClimber;
    private SparkMaxIO leftClimber;

    public ClimberIONeo(int rightClimberID, int leftClimberID) {
        rightClimber = new SparkMaxIO(rightClimberID, MotorType.kBrushless);
        leftClimber = new SparkMaxIO(leftClimberID, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.smartCurrentLimit(1);

        leftClimber.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightClimber.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        leftClimber.getEncoder().setPosition(0);
        rightClimber.getEncoder().setPosition(0);
    }

    public void setPower(double rightPower, double leftPower) {
        rightClimber.set(rightPower);
        leftClimber.set(leftPower);
    }

    public void updateInputs(ClimberIOInputs inputs) {
        inputs.leftPosition = leftClimber.getEncoder().getPosition();
        inputs.rightPosition = rightClimber.getEncoder().getPosition();
        leftClimber.updateInputs(inputs.left);
        rightClimber.updateInputs(inputs.right);
    }
}
