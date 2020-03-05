package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
    private final VictorSPX feederTalon = new VictorSPX(Constants.Talon.SHOOTER_FEEDER);

    /**
     * Creates a new Feeder.
     */
    public Feeder() {

        configureTalon();
    }

    public void init() {
        configureTalon();    }

    private void configureTalon() {
        feederTalon.setNeutralMode(NeutralMode.Brake);
        feederTalon.configNominalOutputForward(+0.0f);
        feederTalon.configNominalOutputReverse(0.0);
        feederTalon.configPeakOutputForward(+12.0);
        feederTalon.configPeakOutputReverse(-6.0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
    }

    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter feeder speed", feederTalon.getMotorOutputVoltage());
    }

    public void setSpeed(double pos) {
        feederTalon.set(ControlMode.PercentOutput, pos);
    }
}
