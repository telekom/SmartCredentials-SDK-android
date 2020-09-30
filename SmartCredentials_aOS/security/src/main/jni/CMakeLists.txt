# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
        repo_alias

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        repo_alias.cpp)