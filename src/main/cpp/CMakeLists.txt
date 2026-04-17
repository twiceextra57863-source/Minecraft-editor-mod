cmake_minimum_required(VERSION 3.10)

project(ultraboost_vulkan)

add_library(
    ultraboost_vulkan
    SHARED
    ultraboost_vulkan.cpp
)

find_library(log-lib log)

target_link_libraries(
    ultraboost_vulkan
    ${log-lib}
)
