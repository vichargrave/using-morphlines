
# using-morphlines 

Morphlines example code being developed for an upcoming book tentatively entitled **Using Morphlines**.

## Build Project Jars

To build the jars, do the following: 

 1. `cd using-morphlines`
 2. Set the `${dict_path}` in *conf/env.conf* to the absolute path of the *conf/dict* directory.
 3. `mvn clean package`

You can also build the jars with a Maven aware IDE like IntelliJ, which is what I use.  To build that way, do the following:

 1. At the main dialog box, click on **Open**.
 2. Navigate to the *using-morphlines* directory.
 3. Click on **Open**.
 4. Click on **Maven Properties** tab to the right of the main window.
 5. Click on **Maven Execute Goal**.
 6. Type `mvn clean package`.
 7. Click on **Execute**.
 
 The jars will be located in the *target* directory of each subproject.
 
 **Note** that you have to set `${dict_path}` for either building method.
