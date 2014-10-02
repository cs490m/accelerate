Accelerate
==========

This project provides a barebones sensor reading application.

It lists the full set of sensors available at startup.  
It then periodically reads barometer data (assuming a barometer is available!).

Extensions should include:

* Enable the user to configure which sensors are being sampled, and how often
* Some mechanism to upload recorded sensor data to a remote server
* (Potentially) compute derived sensor information (user jumped in the air)

Later on, application developers may want to add other screens
(Activities in Android speak) to the application.

You will need [Android Studio](https://developer.android.com/sdk/installing/studio.html)
to build and run the application.  (Try the "Check out from Version Control"
option at startup.)

The Android emulator is normally reasonable for testing, but for obvious 
reasons is unlikely to have very interesting sensor data.  A real Android
phone will probably make development simpler.  You will need to enable 
USB debugging on your phone if it hasn't been already.  How to do this 
depends on the phone:
(Enable USB Debugging)[https://www.google.com/?gws_rd=ssl#q=android+enable+usb+debugging]
