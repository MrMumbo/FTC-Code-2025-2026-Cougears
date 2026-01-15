package org.firstinspires.ftc.teamcode.mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HwChasis {
    // define motors
    public DcMotor  backRight   = null;
    public DcMotor  frontRight  = null;
    public DcMotor  backLeft   = null;
    public DcMotor  frontLeft  = null;
    public DcMotor  shootPower  = null;
    public DcMotor loadPower = null;

    public double shootVel;

    public void init(HardwareMap hwMap) {
        //define motors
        backRight  = hwMap.get(DcMotor.class, "backRight");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backLeft  = hwMap.get(DcMotor.class, "backLeft");
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        shootPower = hwMap.get(DcMotor.class, "shootPower");
        loadPower = hwMap.get(DcMotor.class, "loadPower");



        //set motor directions
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        shootPower.setDirection(DcMotor.Direction.FORWARD);
        loadPower.setDirection(DcMotor.Direction.REVERSE);
    }
}