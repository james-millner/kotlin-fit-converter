# Kotlin Fit Converter

[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg)](https://kotlinlang.org/)
[![Latest Release](https://img.shields.io/badge/0.5.1-alpha-red)](https://github.com/example/garmin-fit-converter/releases)
[![Gradle Build & Test](https://github.com/james-millner/kotlin-fit-converter/actions/workflows/gradle.yml/badge.svg)](https://github.com/james-millner/kotlin-fit-converter/actions/workflows/gradle.yml)

The Kotlin Fit Converter Library is a Kotlin-based utility that allows users to convert Garmin FIT files into different formats.

> The Flexible and Interoperable Data Transfer (FIT) protocol is designed specifically for the storing and sharing of data that originates from sport, fitness and health devices. The FIT protocol defines a set of data storage templates (FIT messages) that can be used to store information such as activity data, courses, and workouts. It is designed to be compact, interoperable and extensible.

The Garmin FIT SDK is property of Garmin International Inc. Please read and accept Garmin license agreement before using this SDK.
To ensure full understanding and legal compliance, users thoroughly review the accompanying [license agreement](https://developer.garmin.com/fit/download/). This agreement provides key information on the permissions, restrictions, and legal responsibilities related to the Garmin FIT SDK.

Source: https://developer.garmin.com/fit/overview/

The version of the Garmin FIT SDK used in this project is **21.171.0.**

## Features

- Convert Garmin FIT files into JSON format.
- Easy-to-use API for seamless integration into your Kotlin or Java applications.
- Currently, is able to convert fit files into:
  - Kotlin Data Classes
  - JSON
  - Protobuf

## Requirements

- Java Development Kit (JDK) 19 or higher.
- Kotlin 1.8.22 or higher.
- Garmin FIT file (.fit) to convert.
