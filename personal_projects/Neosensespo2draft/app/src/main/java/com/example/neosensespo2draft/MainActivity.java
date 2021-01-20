package com.example.neosensespo2draft;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neosensory.neosensoryblessed.NeosensoryBlessed;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    // UI components
    //before connect
    Button connect;
    TextView instruction;
    //after connect
    Button disconnect;
    TextView label1,label2,label3,label4,cliOutput;
    TextView spooTag,temperatureTag,heartrateTag,insightTag;

    // Constants
    private static final int ACCESS_LOCATION_REQUEST = 2;
    private static final int NUM_MOTORS = 4;

    // Access the library to leverage the Neosensory API
    private NeosensoryBlessed blessedNeo = null;


    // Variable to track whether or not the wristband should be vibrating
    private static boolean vibrating = false;
    private static boolean disconnectRequested =
            false; // used for requesting a disconnect within our thread
    Runnable vibratingPattern;
    Thread vibratingPatternThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //preconnect ui
        connect = (Button) findViewById(R.id.connectButton);
        instruction = (TextView) findViewById(R.id.instruction);
        //cli
        cliOutput = (TextView) findViewById(R.id.cli_response);
        //labels
        label1 = (TextView) findViewById(R.id.label1);
        label2=(TextView) findViewById(R.id.label2);
        label3=(TextView) findViewById(R.id.label3);
        label4 =(TextView) findViewById(R.id.label4);
        //textViews
        spooTag = (TextView) findViewById(R.id.spooTag);
        heartrateTag = (TextView) findViewById(R.id.heartrateTag);
        temperatureTag = (TextView) findViewById(R.id.temperatureTag);
        insightTag = (TextView) findViewById(R.id.insightTag);
        //button post connect
        disconnect = (Button) findViewById(R.id.button2);


        displayInitialUI();

        NeosensoryBlessed.requestBluetoothOn(this);

        if(checkLocationPermissions()){
            displayInitConnectButton();
        }


        vibratingPattern= new VibratingPattern();
    }



    class VibratingPattern implements Runnable {
        private int minVibration = 40;
        private int currentVibration = minVibration;

        public void run() {
            // loop until the thread is interrupted
            int motorID = 0;
            while (!Thread.currentThread().isInterrupted() && vibrating) {
                try {
                    Thread.sleep(150);
                    int[] motorPattern = new int[4];
                    motorPattern[motorID] = currentVibration;
                    blessedNeo.vibrateMotors(motorPattern);
                    motorID = (motorID + 1) % NUM_MOTORS;
                    currentVibration = (currentVibration + 1) % NeosensoryBlessed.MAX_VIBRATION_AMP;
                    if (currentVibration == 0) {
                        currentVibration = minVibration;
                    }
                } catch (InterruptedException e) {
                    blessedNeo.stopMotors();
                    blessedNeo.resumeDeviceAlgorithm();
                    Log.i(TAG, "Interrupted thread");
                    e.printStackTrace();
                }
            }
            if (disconnectRequested) {
                Log.i(TAG, "Disconnect requested while thread active");
                blessedNeo.stopMotors();
                blessedNeo.resumeDeviceAlgorithm();
                // When disconnecting: it is possible for the device to process the disconnection request
                // prior to processing the request to resume the onboard algorithm, which causes the last
                // sent motor command to "stick"
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blessedNeo.disconnectNeoDevice();
                disconnectRequested = false;
            }
        }
    }

    //////////////////////////
    // Cleanup on shutdown //
    /////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(BlessedReceiver);
        if (vibrating) {
            vibrating = false;
            disconnectRequested = true;
        }
        blessedNeo = null;
        vibratingPatternThread = null;
    }

    ////////////////////////////////////
    // SDK state change functionality //
    ////////////////////////////////////

    private final BroadcastReceiver BlessedReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.hasExtra("com.neosensory.neosensoryblessed.CliReadiness")) {
                        // Check the message from NeosensoryBlessed to see if a Neosensory Command Line
                        // Interface
                        // has become ready to accept commands
                        // Prior to calling other API commands we need to accept the Neosensory API ToS
                        if (intent.getBooleanExtra("com.neosensory.neosensoryblessed.CliReadiness", false)) {
                            // request developer level access to the connected Neosensory device
                            blessedNeo.sendDeveloperAPIAuth();
                            // sendDeveloperAPIAuth() will then transmit a message back requiring an explicit
                            // acceptance of Neosensory's Terms of Service located at
                            // https://neosensory.com/legal/dev-terms-service/
                            blessedNeo.acceptApiTerms();
                            Log.i(TAG, String.format("state message: %s", blessedNeo.getNeoCliResponse()));
                            // Assuming successful authorization, set up a button to run the vibrating pattern
                            // thread above
                            displayVibrateButton();
                            displayDisconnectUI();
                        } else {
                            displayReconnectUI();
                        }
                    }

                    if (intent.hasExtra("com.neosensory.neosensoryblessed.CliMessage")) {
                        String notification_value =
                                intent.getStringExtra("com.neosensory.neosensoryblessed.CliMessage");
                        cliOutput.setText(notification_value);
                    }

                    if (intent.hasExtra("com.neosensory.neosensoryblessed.ConnectedState")) {
                        if (intent.getBooleanExtra("com.neosensory.neosensoryblessed.ConnectedState", false)) {
                            Log.i(TAG, "Connected to Buzz");
                        } else {
                            Log.i(TAG, "Disconnected from Buzz");
                        }
                    }
                }
            };



    public void displayVibrateButton() {
        cliOutput.setVisibility(View.VISIBLE);
        label1.setVisibility(View.VISIBLE);
        label2.setVisibility(View.VISIBLE);
        label3.setVisibility(View.VISIBLE);
        label4.setVisibility(View.VISIBLE);
        spooTag.setVisibility(View.VISIBLE);
        heartrateTag.setVisibility(View.VISIBLE);
        insightTag.setVisibility(View.VISIBLE);
        temperatureTag.setVisibility(View.VISIBLE);
        connect.setVisibility(View.VISIBLE);

    }

    private void displayDisconnectUI() {
        disconnect.setVisibility(View.VISIBLE);
        disconnect.setClickable(true);
        disconnect.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!vibrating) {
                            blessedNeo.disconnectNeoDevice();
                        } else {
                            // If motors are vibrating (in the VibratingPattern thread in this case) and we want
                            // to stop them on disconnect, we need to add a sleep/delay as it's possible for the
                            // disconnect to be processed prior to stopping the motors. See the VibratingPattern
                            // definition.
                            disconnectRequested = true;
                            vibrating = false;
                        }
                    }
                });
    }

    private void displayReconnectUI() {
        cliOutput.setVisibility(View.GONE);
        label1.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        label4.setVisibility(View.GONE);

        disconnect.setVisibility(View.GONE);
        connect.setVisibility(View.VISIBLE);
        connect.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        blessedNeo.attemptNeoReconnect();
                        toastMessage("Attempting to reconnect. This may take a few seconds.");
                        if (!vibrating) {
                            blessedNeo.pauseDeviceAlgorithm();
                            toastMessage("Stop Vibration Pattern");
                            vibrating = true;
                            // run the vibrating pattern loop
                            vibratingPatternThread = new Thread(vibratingPattern);
                            vibratingPatternThread.start();
                        } else {
                            toastMessage("Start Vibration Pattern");
                            vibrating = false;
                            blessedNeo.resumeDeviceAlgorithm();
                        }
                    }
                });
    }

    private void displayInitConnectButton() {
        connect.setVisibility(View.VISIBLE);
        connect.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initBluetoothHandler();
                    }
                }
        );
    }


    private void displayInitialUI() {
        displayReconnectUI();

    }

    public void connect(View view) {
        Toast.makeText(this, "button pressed", Toast.LENGTH_SHORT).show();
    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    //////////////////////////////////////////////
    // Bluetooth and permissions initialization //
    //////////////////////////////////////////////

    private void initBluetoothHandler() {
        // Create an instance of the Bluetooth handler. This uses the constructor that will search for
        // and connect to the first available device with "Buzz" in its name. To connect to a specific
        // device with a specific address, you can use the following pattern:  blessedNeo =
        // NeosensoryBlessed.getInstance(getApplicationContext(), <address> e.g."EB:CA:85:38:19:1D",
        // false);
        blessedNeo =
                NeosensoryBlessed.getInstance(getApplicationContext(), new String[] {"Buzz"}, false);
        // register receivers so that NeosensoryBlessed can pass relevant messages and state changes to MainActivity
        registerReceiver(BlessedReceiver, new IntentFilter("BlessedBroadcast"));
    }


    private boolean checkLocationPermissions() {
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                && targetSdkVersion >= Build.VERSION_CODES.Q) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST);
                return false;
            } else {
                return true;
            }
        } else {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_LOCATION_REQUEST);
                return false;
            } else {
                return true;
            }
        }
    }

}


