/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2016. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

/**
 * REV Robotics SPARK Speed Controller
 */
public class Spark extends SafePWM implements SpeedController, IDeviceController {
  private boolean isInverted = false;

  /**
   * Common initialization code called by all constructors.
   *
   * Note that the SPARK uses the following bounds for PWM values. These
   * values should work reasonably well for most controllers, but if users
   * experience issues such as asymmetric behavior around the deadband or
   * inability to saturate the controller in either direction, calibration is
   * recommended. The calibration procedure can be found in the Spark User
   * Manual available from REV Robotics.
   *
   * - 2.003ms = full "forward" - 1.55ms = the "high end" of the deadband range
   * - 1.50ms = center of the deadband range (off) - 1.46ms = the "low end" of
   * the deadband range - .999ms = full "reverse"
   */
  protected void initSpark() {
    setBounds(2.003, 1.55, 1.50, 1.46, .999);
    setPeriodMultiplier(PeriodMultiplier.k1X);
    setRaw(m_centerPwm);

    // HMMMM, hope this isn't important! Not available in 2014 code base
    // setZeroLatch();

    LiveWindow.addActuator("Spark", getModuleNumber(), getChannel(), this);
    // Not sure about this
    UsageReporting.report(UsageReporting.kResourceType_Talon, getChannel(), getModuleNumber() - 1);
  }

  /**
   * Constructor.
   *
   * @param channel The PWM channel that the SPARK is attached to. 0-9 are
   *        on-board, 10-19 are on the MXP port
   */
  public Spark(final int channel) {
    super(channel);
    initSpark();
  }

  /**
   * Set the PWM value.
   *
   * @deprecated For compatibility with CANJaguar
   *
   *             The PWM value is set using a range of -1.0 to 1.0,
   *             appropriately scaling the value for the FPGA.
   *
   * @param speed The speed to set. Value should be between -1.0 and 1.0.
   * @param syncGroup The update group to add this Set() to, pending
   *        UpdateSyncGroup(). If 0, update immediately.
   */
  public void set(double speed, byte syncGroup) {
    setSpeed(isInverted ? -speed : speed);
    Feed();
  }

  /**
   * Set the PWM value.
   *
   * The PWM value is set using a range of -1.0 to 1.0, appropriately scaling
   * the value for the FPGA.
   *
   * @param speed The speed value between -1.0 and 1.0 to set.
   */
  public void set(double speed) {
    setSpeed(isInverted ? -speed : speed);
    Feed();
  }

  /**
   * Common interface for inverting direction of a speed controller
   *
   * @param isInverted The state of inversion true is inverted
   */
  public void setInverted(boolean isInverted) {
    this.isInverted = isInverted;
  }

  /**
   * Common interface for the inverting direction of a speed controller.
   *
   * @return isInverted The state of inversion, true is inverted.
   *
   */
  public boolean getInverted() {
    return this.isInverted;
  }

  /**
   * Get the recently set value of the PWM.
   *
   * @return The most recently set value for the PWM between -1.0 and 1.0.
   */
  public double get() {
    return getSpeed();
  }

  /**
   * Write out the PID value as seen in the PIDOutput base object.
   *
   * @param output Write out the PWM value as was found in the PIDController
   */
  public void pidWrite(double output) {
    set(output);
  }
}
