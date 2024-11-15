package frc.robot.subsystems.Intake.Rollers;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import frc.lib.g3.FrictionMotorSim;
import frc.lib.g3.MotorIOInputs;
import frc.robot.Constants;

public class RollerIOSim implements RollerIO {
    private final FrictionMotorSim externalMotorSim = new FrictionMotorSim(DCMotor.getNeo550(1), Units.KilogramSquareMeters.of(0.001), false);
    private final FrictionMotorSim internalMotorSim = new FrictionMotorSim(DCMotor.getNeo550(1), Units.KilogramSquareMeters.of(0.001), false);

    public RollerIOSim() {
        externalMotorSim.inverted(true);
    }

    @Override
    public void setPower(double power) {
        externalMotorSim.set(power);
        internalMotorSim.set(power);
    }

    @Override
    public void updateInputs(RollerIOInputs inputs, MotorIOInputs internal, MotorIOInputs external) {
        externalMotorSim.update(Constants.loopTime);
        internalMotorSim.update(Constants.loopTime);

        externalMotorSim.updateInputs(external);
        internalMotorSim.updateInputs(internal);

        inputs.externalPosition = externalMotorSim.getPosition();
        inputs.internalPosition = internalMotorSim.getPosition();
    }
}
