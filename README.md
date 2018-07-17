# using-morphlines 

Morphlines example code being developed for an upcoming book tentatively entitled **Using Morphlines**.

## Build Project Jars

To build this project, do the following: 

    1. cd using-mmorphlines
    2. Set the ${dict_path} in conf/env.conf to the absolute path of the conf/dict directory.
    3. mvn clean package

Alternatively, and perhaps more easily, you can work with this code by building a project in an IDE like IntelliJ, which is what I used for the development of this code.  To do that just open the *using-morphlines* directory in IntelliJ. **Note** you still have to set the `${dict_path}` variable when using IntelliJ.