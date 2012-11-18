# SciGraph Calculator
### by Sam Pullman

This is a scientific graphing calculator with the following features:
* Main calculator mode with trig functions, complex number calculations, previous input/result storage, and more.
* A conversion mode with many different conversion types.
* A graphing calculator that can plot up to three arbitrary functions at once.
* Tabs corresponding to different modes can be swiped between.
* Graph can be moved, zoomed and reset, there is a zeros button, and trace mode.
* Portrait and landscape modes supported.
* Copy/paste between tabs

## Instructions for getting this project up and running from the command line.

If you are an Eclipse/IntelliJ/NetBeans user you should replace any shell commands with an action appropriate to your IDE.
The 'update command' I will be referring to is simply:
android update project -p .

1. Clone this repository to a directory of your choice. This directory will be referred to as PROJ_HOME.

2. Download Action Bar Sherlock here:  http://actionbarsherlock.com/ and extract the contents of the library subfolder into a folder called abs_library in the directory PROJ_HOME resides in (NOT into PROJ_HOME itself). Run the update command in the abs_library folder. If you already have ABS somewhere on your computer, you can change the reference in PROJ_HOME/calcAppLib/project.properties to point to its location.

3. cd into PROJ_HOME/calcAppLib/gridlayout/ and run the update command.

4. cd into PROJ_HOME/calcAppLib/ (up one level) and run the update command.

5. cd into PROJ_HOME/calcAppPaid/ and run the update command. If you wish to build the free version, run the command in calcAppFree.

6. The project is now ready to be built. There is a script included in the Paid/Free directories called "run" that cleans, debugs, and installs the app on whatever device you have connected. You can use the standard ant commands (clean, debug, etc.), although to release you would have to use your own keystore and refer to it in the ant.properties file in both the Lib and Paid/Free directories.

7. Make changes in the calcAppLib project, and they will be reflected in both the Paid and Free projects. All you need to do is build the Paid/Free project and install it. Note that the Lib project cannot be installed as a standalone app.

8. Let me know if you have any issues or questions.