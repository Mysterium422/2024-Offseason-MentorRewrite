package frc.robot.sensors.Gyro.GyroIO;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;

public class GyroIOSim implements GyroIO {
    private final AnalogGyroSim m_gyro;

    public GyroIOSim(AnalogGyroSim gyro) {
        m_gyro = gyro;
    }

    @Override
    public void resetHeading(Angle heading) {
        m_gyro.setAngle(heading.in(Units.Rotations));
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.yaw = Units.Rotations.of(m_gyro.getAngle());
    }
    
}
