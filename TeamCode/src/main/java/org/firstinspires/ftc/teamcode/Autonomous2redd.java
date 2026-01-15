package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: ShootDriveAutoRed", group="Robot")
public class Autonomous2redd extends LinearOpMode {

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
        double driveTime = 0.5;
        double shootTime = 1.5;
        double loadTime = 1.8;

        double shootSpeed = 1.0;
        double loadSpeed = 1.0;
        double drivepowerRight = 250.0;
        double drivepowerLeft = 275.0;
        
        //drive power
        frontRight.setPower(drivepowerRight);
        frontLeft.setPower(drivepowerLeft);
        backRight.setPower(drivepowerRight);
        backLeft.setPower(drivepowerLeft);
        runtime.reset();

        // Start shooter motor
        shootPower.setPower(shootSpeed);
        runtime.reset();
        
        //run drive
        while(opModeIsActive() && runtime.seconds() < driveTime) {
            telemetry.addData("Drive...", runtime.seconds());
            telemetry.update();
        }
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        // Run shooter
        while (opModeIsActive() && runtime.seconds() < shootTime) {
            telemetry.addData("Shooting...", runtime.seconds());
            telemetry.update();
        }
        shootPower.setPower(0);

        // Run loader
        loadPower.setPower(loadSpeed);
        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < loadTime) {
            telemetry.addData("Loading...", runtime.seconds());
            telemetry.update();
        }
        loadPower.setPower(0);
        
        // while(opModeIsActive() && runtime.seconds() < driveTime){
        //     telemetry.addData("Move...", drivTime());
        //     telemetry.update();
        // }
        // frontRight.setPower(0);
        // frontLeft.setPower(0);
        // backRight.setPower(0);
        // backLeft.setPower(0);
        
        telemetry.addData("Status", "Done");
        telemetry.update();
    }
}
