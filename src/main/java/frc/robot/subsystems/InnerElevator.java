package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.util.Util;

import frc.robot.subsystems.Remote;
import frc.robot.subsystems.Remote.ElevatorMode;


public class InnerElevator {
    private final TalonSRX motor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Elevator_Inner);

    private final PIDController pid = new PIDController(0, 0, 0);

    private final double maxPos = 0.80; // 0.80 meters

    private final Encoder encoder = new Encoder(6, 7);

    // private static SimplePID innerPid = new SimplePID("elevatorInner", 12);
    // private static SimplePID outerPid = new SimplePID("elevatorOuter", 12);

    double targetPos = 0.00;
    boolean motorEnabled = false;

    double getPrefDouble(String key, double backup) {
        return Preferences.getDouble("elevPid_" + key, backup);
    }
    void setPrefDouble(String key, double value) {
        Preferences.setDouble("elevPid_" + key, value);
    }

    public InnerElevator() {
        double kP = getPrefDouble("outerP", 0.01);
        double kI = getPrefDouble("outerI", 0.00);
        double kD = getPrefDouble("outerD", 0.00);
        setPrefDouble("outerP", kP);
        setPrefDouble("outerI", kI);
        setPrefDouble("outerD", kD);
        config();
    }

    public void setTargetPos(double value) {
        targetPos = value;
    }

    public double modeToPos(ElevatorMode mode){
        switch (mode) {
            case L1:
              return Constants.ReefLayers.L1;
            case L2: // TODO
              return Constants.ReefLayers.L2;
            case L3:
                return Constants.ReefLayers.L3;
            case L4:
                return Constants.ReefLayers.L4;
            case Idle:
            default:
             return 0.;
          }
    }

    public void setEnabled(boolean value) {
        motorEnabled = value;
    }

    // progress: 0 ~ 1
    public double calculateFactor(double progress) {
        return Util.lerp(Util.clamp(progress, 0., 1.), 1., 1.8);
    }

    public void config() {
        motor.setNeutralMode(NeutralMode.Coast);

        double dpr = 23 * 1.5; // distance per revolution
        double ppr = 1024.; // pulses per revolution
        encoder.setDistancePerPulse(dpr / ppr);

        double kP = getPrefDouble("outerP", 0.01);
        double kI = getPrefDouble("outerI", 0.00);
        double kD = getPrefDouble("outerD", 0.00);

        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
    }

    double calculateOutput(double currentPos) {
        if (motorEnabled) {
            return pid.calculate(currentPos, targetPos);

            /* double factor = calculateFactor(currentPos / maxPos);
            // outerMotor.set(TalonSRXControlMode.PercentOutput, output);

            if (Math.abs(targetPos - currentPos) > 0.01) {
                if (currentPos < targetPos) {
                    // go up
                    return 0.60 * factor;
                } else {
                    // go down
                    return 0.35 * factor;
                }
            } else {
                return 0;
            } */
        } else {
            return 0;
        }
    }

    public void mainloop() {
        double currentPos = encoder.getDistance();
        setTargetPos(modeToPos(Remote.getElevatorMode()));
        

        double output = calculateOutput(currentPos);
        output = Util.clamp(output, 0, 0.9);

        motor.set(ControlMode.PercentOutput, output);
        SmartDashboard.putNumber("elevatorInnerEncoder", currentPos);
        SmartDashboard.putNumber("elevatorInnerOutput", output);
    }
}
