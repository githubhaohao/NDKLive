# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             live

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/jni/live.c src/main/jni/queue.c)

include_directories(${CMAKE_SOURCE_DIR}/src/main/jni/include)
set(jnilibs "${CMAKE_SOURCE_DIR}/src/main/jniLibs")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${jnilibs}/${ANDROID_ABI})

add_library(faac STATIC IMPORTED )
set_target_properties(faac PROPERTIES IMPORTED_LOCATION "${jnilibs}/${ANDROID_ABI}/libfaac.a")

add_library(rtmpdump STATIC IMPORTED )
set_target_properties(rtmpdump PROPERTIES IMPORTED_LOCATION "${jnilibs}/${ANDROID_ABI}/librtmp.a")

add_library(x264 STATIC IMPORTED )
set_target_properties(x264 PROPERTIES IMPORTED_LOCATION "${jnilibs}/${ANDROID_ABI}/libx264.a")



find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
find_library(
            android-lib
            android
            )




target_link_libraries( # Specifies the target library.
                       live
                       ${log-lib}
                       ${android-lib}
                       x264
                       faac
                       rtmpdump
                       )