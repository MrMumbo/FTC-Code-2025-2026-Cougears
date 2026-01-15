package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: longdrivered", group="Robot")
public class longdriveredd extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor shootPower = null;
    private DcMotor loadPower = null;
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    @Override
    public void runOpMode() {

        shootPower = hardwareMap.get(DcMotor.class, "shootPower");
        loadPower  = hardwareMap.get(DcMotor.class, "loadPower");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        shootPower.setDirection(DcMotor.Direction.FORWARD);
        loadPower.setDirection(DcMotor.Direction.REVERSE);
        
        

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        waitForStart();
        double driveTime = 3.0;
        double shootTime = 3.0;
        double loadTime = 4.0;
        //double turnTime = 0.5;

// Powers
        double drivePower = 1.0;
        double shootSpeed = 0.75;
        double loadSpeed  = 1.0;

        runtime.reset();
        frontRight.setPower(drivePower);
        frontLeft.setPower(drivePower);
        backRight.setPower(drivePower);
        backLeft.setPower(drivePower);

        while (opModeIsActive() && runtime.seconds() < driveTime) {
            telemetry.addData("Driving", runtime.seconds());
            telemetry.update();
        }

        stopDrive();

        
        shootPower.setPower(shootSpeed);
        runtime.reset();
        
        while (opModeIsActive() && runtime.seconds() < shootTime) {
            telemetry.addData("Shooting", runtime.seconds());
            telemetry.update();
        }

        shootPower.setPower(0);

        runtime.reset();
        loadPower.setPower(loadSpeed);

        while (opModeIsActive() && runtime.seconds() < loadTime) {
            telemetry.addData("Loading", runtime.seconds());
            telemetry.update();
        }

        loadPower.setPower(0);

        telemetry.addData("Status", "Done");
        telemetry.update();
    }

    private void stopDrive() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }
}

