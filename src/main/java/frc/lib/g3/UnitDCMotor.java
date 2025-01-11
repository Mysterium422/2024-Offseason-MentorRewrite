package frc.lib.g3;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Torque;
import edu.wpi.first.units.measure.Voltage;

public class UnitDCMotor extends DCMotor {
  public UnitDCMotor(
      double nominalVoltageVolts,
      double stallTorqueNewtonMeters,
      double stallCurrentAmps,
      double freeCurrentAmps,
      double freeSpeedRadPerSec,
      int numMotors) {
    super(
        nominalVoltageVolts,
        stallTorqueNewtonMeters,
        stallCurrentAmps,
        freeCurrentAmps,
        freeSpeedRadPerSec,
        numMotors);
  }

  public static UnitDCMotor unitize(DCMotor motor) {
    // Can do 1 since multiplier has already been applied
    return new UnitDCMotor(
        motor.nominalVoltageVolts,
        motor.stallTorqueNewtonMeters,
        motor.stallCurrentAmps,
        motor.freeCurrentAmps,
        motor.freeSpeedRadPerSec,
        1);
  }

  /**
   * Calculate current drawn by motor with given speed and input voltage.
   *
   * @param speed The current angular velocity of the motor.
   * @param voltage The voltage being applied to the motor.
   * @return The estimated current.
   */
  public Current getCurrent(AngularVelocity speed, Voltage voltage) {
    return Units.Amps.of(getCurrent(speed.in(Units.RadiansPerSecond), voltage.in(Units.Volts)));
  }

  /**
   * Calculate current drawn by motor for a given torque.
   *
   * @param torque The torque produced by the motor.
   * @return The current drawn by the motor.
   */
  public Current getCurrent(Torque torque) {
    return Units.Amps.of(getCurrent(torque.in(Units.NewtonMeters)));
  }

  /**
   * Calculate torque produced by the motor with a given current.
   *
   * @param current The current drawn by the motor.
   * @return The torque output.
   */
  public Torque getTorque(Current current) {
    return Units.NewtonMeters.of(getTorque(current.in(Units.Amps)));
  }

  /**
   * Calculate the voltage provided to the motor for a given torque and angular velocity.
   *
   * @param torque The torque produced by the motor.
   * @param speed The current angular velocity of the motor.
   * @return The voltage of the motor.
   */
  public Voltage getVoltage(Torque torque, AngularVelocity speed) {
    return Units.Volts.of(
        getVoltage(torque.in(Units.NewtonMeters), speed.in(Units.RadiansPerSecond)));
  }

  /**
   * Calculates the angular speed produced by the motor at a given torque and input voltage.
   *
   * @param torque The torque produced by the motor.
   * @param voltage The voltage applied to the motor.
   * @return The angular speed of the motor.
   */
  public AngularVelocity getSpeed(Torque torque, Voltage voltage) {
    return Units.RadiansPerSecond.of(
        getSpeed(torque.in(Units.NewtonMeters), voltage.in(Units.Volts)));
  }

  /**
   * Returns a copy of this motor with the given gearbox reduction applied.
   *
   * @param gearboxReduction The gearbox reduction.
   * @return A motor with the gearbox reduction applied.
   */
  @Override
  public UnitDCMotor withReduction(double gearboxReduction) {
    return new UnitDCMotor(
        nominalVoltageVolts,
        stallTorqueNewtonMeters * gearboxReduction,
        stallCurrentAmps,
        freeCurrentAmps,
        freeSpeedRadPerSec / gearboxReduction,
        1);
  }

  /**
   * Return a gearbox of CIM motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of CIM motors.
   */
  public static UnitDCMotor getCIM(int numMotors) {
    return new UnitDCMotor(
        12,
        2.42,
        133,
        2.7,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(5310),
        numMotors);
  }

  /**
   * Return a gearbox of 775Pro motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of 775Pro motors.
   */
  public static UnitDCMotor getVex775Pro(int numMotors) {
    return new UnitDCMotor(
        12,
        0.71,
        134,
        0.7,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(18730),
        numMotors);
  }

  /**
   * Return a gearbox of NEO motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of NEO motors.
   */
  public static UnitDCMotor getNEO(int numMotors) {
    return new UnitDCMotor(
        12,
        2.6,
        105,
        1.8,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(5676),
        numMotors);
  }

  /**
   * Return a gearbox of MiniCIM motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of MiniCIM motors.
   */
  public static UnitDCMotor getMiniCIM(int numMotors) {
    return new UnitDCMotor(
        12,
        1.41,
        89,
        3,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(5840),
        numMotors);
  }

  /**
   * Return a gearbox of Bag motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Bag motors.
   */
  public static UnitDCMotor getBag(int numMotors) {
    return new UnitDCMotor(
        12,
        0.43,
        53,
        1.8,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(13180),
        numMotors);
  }

  /**
   * Return a gearbox of Andymark RS775-125 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Andymark RS775-125 motors.
   */
  public static UnitDCMotor getAndymarkRs775_125(int numMotors) {
    return new UnitDCMotor(
        12,
        0.28,
        18,
        1.6,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(5800.0),
        numMotors);
  }

  /**
   * Return a gearbox of Banebots RS775 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Banebots RS775 motors.
   */
  public static UnitDCMotor getBanebotsRs775(int numMotors) {
    return new UnitDCMotor(
        12,
        0.72,
        97,
        2.7,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(13050.0),
        numMotors);
  }

  /**
   * Return a gearbox of Andymark 9015 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Andymark 9015 motors.
   */
  public static UnitDCMotor getAndymark9015(int numMotors) {
    return new UnitDCMotor(
        12,
        0.36,
        71,
        3.7,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(14270.0),
        numMotors);
  }

  /**
   * Return a gearbox of Banebots RS 550 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Banebots RS 550 motors.
   */
  public static UnitDCMotor getBanebotsRs550(int numMotors) {
    return new UnitDCMotor(
        12,
        0.38,
        84,
        0.4,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(19000.0),
        numMotors);
  }

  /**
   * Return a gearbox of NEO 550 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of NEO 550 motors.
   */
  public static UnitDCMotor getNeo550(int numMotors) {
    return new UnitDCMotor(
        12,
        0.97,
        100,
        1.4,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(11000.0),
        numMotors);
  }

  /**
   * Return a gearbox of Falcon 500 motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Falcon 500 motors.
   */
  public static UnitDCMotor getFalcon500(int numMotors) {
    return new UnitDCMotor(
        12,
        4.69,
        257,
        1.5,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(6380.0),
        numMotors);
  }

  /**
   * Return a gearbox of Falcon 500 motors with FOC (Field-Oriented Control) enabled.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Falcon 500 FOC enabled motors.
   */
  public static UnitDCMotor getFalcon500Foc(int numMotors) {
    // https://store.ctr-electronics.com/falcon-500-powered-by-talon-fx/
    return new UnitDCMotor(
        12,
        5.84,
        304,
        1.5,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(6080.0),
        numMotors);
  }

  /**
   * Return a gearbox of Romi/TI_RSLK MAX motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Romi/TI_RSLK MAX motors.
   */
  public static UnitDCMotor getRomiBuiltIn(int numMotors) {
    // From https://www.pololu.com/product/1520/specs
    return new UnitDCMotor(
        4.5,
        0.1765,
        1.25,
        0.13,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(150.0),
        numMotors);
  }

  /**
   * Return a gearbox of Kraken X60 brushless motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return a gearbox of Kraken X60 motors.
   */
  public static UnitDCMotor getKrakenX60(int numMotors) {
    // From https://store.ctr-electronics.com/announcing-kraken-x60/
    return new UnitDCMotor(
        12,
        7.09,
        366,
        2,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(6000),
        numMotors);
  }

  /**
   * Return a gearbox of Kraken X60 brushless motors with FOC (Field-Oriented Control) enabled.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return A gearbox of Kraken X60 FOC enabled motors.
   */
  public static UnitDCMotor getKrakenX60Foc(int numMotors) {
    // From https://store.ctr-electronics.com/announcing-kraken-x60/
    return new UnitDCMotor(
        12,
        9.37,
        483,
        2,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(5800),
        numMotors);
  }

  /**
   * Return a gearbox of Neo Vortex brushless motors.
   *
   * @param numMotors Number of motors in the gearbox.
   * @return a gearbox of Neo Vortex motors.
   */
  public static UnitDCMotor getNeoVortex(int numMotors) {
    // From https://www.revrobotics.com/next-generation-spark-neo/
    return new UnitDCMotor(
        12,
        3.60,
        211,
        3.6,
        edu.wpi.first.math.util.Units.rotationsPerMinuteToRadiansPerSecond(6784),
        numMotors);
  }
}
