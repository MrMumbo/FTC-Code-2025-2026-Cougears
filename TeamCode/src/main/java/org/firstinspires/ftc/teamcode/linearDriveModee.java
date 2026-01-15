package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.HwChasis;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp(name="Robot: Shoot Drive", group="Robot")
public class  linearDriveModee extends OpMode {
    private AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    private HwChasis hwchasis = new HwChasis();
    public boolean selectAT = false;
    public double ATDis = 0.0f;
    public double ATRot = 0.0f;
    public double ATTag = 0.0f;
    public double realRot = 0.0f;
    boolean dPadDown = false;


    @Override
    public void init() {
        hwchasis.init(hardwareMap);
        aprilTagWebcam.init(hardwareMap,telemetry);
    }
    
    @Override
    public void init_loop() {
       
    }
    
    @Override
    public void start() {
        aprilTagWebcam.update();
    }

    @Override
    public void loop() { 

        //drive var declaration
        float horizantalMovement = gamepad2.left_stick_x;
        float verticalMovement = gamepad2.left_stick_y;
        float rotationalMovement = gamepad2.right_stick_x;
        float shootSpeed = gamepad1.x ? 0.85f : 0;
        float loadSpeed = gamepad1.a ? 1 : 0;
        float revloadBall = gamepad1.b ? 1 : 0;
        float wheelIncrease = gamepad1.y ? 1 : 0;
        if(gamepad1.dpad_down){dPadDown = !dPadDown;}
        //drive system(strafe, turn, forward/backward)

        if(dPadDown){
            hwchasis.backLeft.setPower(-verticalMovement - horizantalMovement + rotationalMovement);
            hwchasis.frontLeft.setPower(-verticalMovement + horizantalMovement + rotationalMovement);
            hwchasis.backRight.setPower(-verticalMovement + horizantalMovement - rotationalMovement);
            hwchasis.frontRight.setPower(-verticalMovement - horizantalMovement - rotationalMovement);
        } else {
            hwchasis.backLeft.setPower(-verticalMovement - horizantalMovement - (ATRot * 0.1));
            hwchasis.frontLeft.setPower(-verticalMovement + horizantalMovement - (ATRot * 0.1));
            hwchasis.backRight.setPower(-verticalMovement + horizantalMovement + (ATRot * 0.1));
            hwchasis.frontRight.setPower(-verticalMovement - horizantalMovement + (ATRot * 0.1));
        }

        hwchasis.shootPower.setPower(shootSpeed + wheelIncrease/4);
        hwchasis.loadPower.setPower(loadSpeed);
        if (revloadBall == 1) {
            hwchasis.loadPower.setDirection(DcMotor.Direction.FORWARD);
            hwchasis.loadPower.setPower(1);
            hwchasis.shootPower.setDirection(DcMotor.Direction.REVERSE);
            hwchasis.shootPower.setPower(0.25 + wheelIncrease/2);
        } 
        if (revloadBall == 0) {
            hwchasis.loadPower.setDirection(DcMotor.Direction.REVERSE);
            hwchasis.loadPower.setPower(loadSpeed);
            hwchasis.shootPower.setDirection(DcMotor.Direction.FORWARD);
            hwchasis.shootPower.setPower(shootSpeed + wheelIncrease/4);
        }
        //shoot motors
        //0 = frontRight


        //braking movement system
        if((horizantalMovement == 0.00f) && (verticalMovement == 0.00f) && (rotationalMovement == 0.00f) && (shootSpeed == 0.00f)) {
            hwchasis.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hwchasis.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hwchasis.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hwchasis.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            hwchasis.shootPower.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        aprilTagWebcam.update();

        java.util.List<AprilTagDetection> detections = aprilTagWebcam.getDetectedTags();

        if (!detections.isEmpty()) {
            AprilTagDetection tag = detections.get(0);
            if (tag.id == 24 || tag.id == 20){
                if(selectAT && tag.id == 20){
                    telemetry.addData("Selected AT", tag.id);
                    telemetry.addData("distance to AT (cm)", tag.ftcPose.y);
                    telemetry.addData("rotation to AT (cm)", tag.ftcPose.bearing);
                    ATRot = tag.ftcPose.bearing;
                    realRot = tag.ftcPose.bearing;
                    ATDis = tag.ftcPose.y;
                    ATTag = tag.id;
                } else {
                    if (selectAT){
                        realRot = 0.0f;
                        ATDis = 0.0f;
                        ATRot = 0.0f;
                        ATTag = 0.0f;
                    }
                }

                if(!selectAT && tag.id == 24){
                    telemetry.addData("Selected AT", tag.id);
                    telemetry.addData("distance to AT (cm)", tag.ftcPose.y);
                    telemetry.addData("rotation to AT (cm)", tag.ftcPose.bearing);
                    ATRot = tag.ftcPose.bearing;
                    realRot = tag.ftcPose.bearing;
                    ATDis = tag.ftcPose.y;
                    ATTag = tag.id;
                } else {
                    if (!selectAT){
                        ATDis = 0.0f;
                        ATRot = 0.0f;
                        ATTag = 0.0f;
                        realRot = 0.0f;
                    }
                }
            }
        } else {
            telemetry.addLine("No tags detected");
            ATDis = 0.0f;
            ATTag = 0.0f;
            realRot = 0.0f;
            ATRot = 0.0f;
        }

        //send telemetry
        telemetry.addData("horizantalMovement",  "%.2f", horizantalMovement);
        telemetry.addData("verticalMovement", "%.2f", verticalMovement);
        telemetry.addData("rotationalMovement",  "%.2f", rotationalMovement);
        telemetry.addData("ball shoot",  "%.2f", shootSpeed);
        telemetry.addData("load ball", "%.2f", loadSpeed);
        telemetry.addData("rev ball", "%.2f", revloadBall);
//        telemetry.addData("rev POD", "%.2f", dPadDown);
    }

    @Override
    public void stop() {
    }
}
