package frc.robot.subsystems.Intake;

import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.lib.g3.UnitUtil;
import frc.robot.subsystems.Intake.Rollers.RollerIO.RollerIOInputs;
import lombok.Getter;

public class IntakeViz {

    @Getter private final Mechanism2d viz;
    private final MechanismLigament2d firstWheelPosition;
    private final MechanismLigament2d secondWheelPosition;
    private final MechanismLigament2d thirdWheelPosition;

    public IntakeViz() {
        viz = new Mechanism2d(30, 20);
        viz.getRoot("RampStart", 14, 6).append(new MechanismLigament2d("RampLine", 10, 20, 4, new Color8Bit(Color.kWhite)));
        
        firstWheelPosition = viz.getRoot("FirstWheel", 3, 8).append(new MechanismLigament2d("FirstWheelPosition", 2, 0, 7, new Color8Bit(Color.kWhite)));
        secondWheelPosition = viz.getRoot("SecondWheel", 10, 3).append(new MechanismLigament2d("SecondWheelPosition", 2, 0, 7, new Color8Bit(Color.kWhite)));
        thirdWheelPosition = viz.getRoot("ThirdWheel", 14, 12).append(new MechanismLigament2d("ThirdWheelPosition", 2, 0, 7, new Color8Bit(Color.kWhite)));
    }

    public void updateViz(RollerIOInputs inputs) {
        firstWheelPosition.setAngle(-UnitUtil.reduceAngle(inputs.externalPosition.divide(16)).in(Units.Degrees));
        secondWheelPosition.setAngle(UnitUtil.reduceAngle(inputs.externalPosition.divide(16)).in(Units.Degrees));
        thirdWheelPosition.setAngle(UnitUtil.reduceAngle(inputs.externalPosition.divide(16)).in(Units.Degrees));
    }
    
}
