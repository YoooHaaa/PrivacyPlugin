# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)


project("privacyplugin")

add_definitions(-w) #忽略所有警告

if(${CMAKE_ANDROID_ARCH_ABI} STREQUAL "arm64-v8a")
    set(OS_DEPENDENDED_SRC
            archs/arm/arm64/assembler/assembler_arm64.cpp
            archs/arm/arm64/inst/inst_arm64.cpp
            archs/arm/arm64/register/register_arm64.cpp
            archs/arm/arm64/register/register_list_arm64.cpp
            archs/arm/arm64/decoder/decoder_arm64.cpp
            archs/arm/arm64/relocate/code_relocate_arm64.cpp
            archs/arm/arm64/hook/hook_arm64.cpp)

elseif (${CMAKE_ANDROID_ARCH_ABI} STREQUAL "armeabi-v7a")
    set(OS_DEPENDENDED_SRC
            archs/arm/arm32/inst/inst_arm32.cpp
            archs/arm/arm32/inst/inst_t32.cpp
            archs/arm/arm32/inst/inst_t16.cpp
            archs/arm/arm32/register/register_arm32.cpp
            archs/arm/arm32/register/register_list_arm32.cpp
            archs/arm/arm32/assembler/assembler_arm32.cpp
            archs/arm/arm32/decoder/decoder_arm32.cpp
            archs/arm/arm32/hook/hook_arm32.cpp
            archs/arm/arm32/hook/breakpoint_shellcode.S
            archs/arm/arm32/relocate/code_relocate_arm32.cpp)
endif()

add_library(
        yooha

        SHARED

        native-lib.cpp
        sandhook_native.cpp
        decoder/decoder.cpp
        relocate/code_relocate.cpp
        elf/elf.cpp
        assembler/assembler.cpp
        buffer/code_buffer.cpp
        utils/platform.cpp
        hook/hook.cpp
        stack/stack.cpp
        ${OS_DEPENDENDED_SRC})


include_directories(
        asm
        decoder
        elf
        utils
        includes
        buffer
        archs/arm
        archs/arm/arm64/inst
        archs/arm/arm64/register
        archs/arm/arm64/decoder
        archs/arm/arm64/assembler
        archs/arm/arm64/relocate
        archs/arm/arm64/hook
        archs/arm/arm32/inst
        archs/arm/arm32/register
        archs/arm/arm32/assembler
        archs/arm/arm32/decoder
        archs/arm/arm32/hook
        archs/arm/arm32/relocate
        src/main/cpp/antihook
)

find_library(
        log-lib

        log)

target_link_libraries(
        yooha

        ${log-lib})

add_definitions(-std=c++11)

ENABLE_LANGUAGE(ASM)