# Kotlin Fit Converter

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-1.8.10-blue.svg)](https://kotlinlang.org/)
[![Latest Release](https://img.shields.io/badge/release-v1.0.0-brightgreen.svg)](https://github.com/example/garmin-fit-converter/releases)
[![Gradle Build & Test](https://github.com/james-millner/kotlin-fit-converter/actions/workflows/gradle.yml/badge.svg)](https://github.com/james-millner/kotlin-fit-converter/actions/workflows/gradle.yml)

The Kotlin Fit Converter Library is a Kotlin-based utility that allows users to convert Garmin FIT files into different formats. Currently, JSON format is supported, and more options will be added in future releases.

> The Flexible and Interoperable Data Transfer (FIT) protocol is designed specifically for the storing and sharing of data that originates from sport, fitness and health devices. The FIT protocol defines a set of data storage templates (FIT messages) that can be used to store information such as activity data, courses, and workouts. It is designed to be compact, interoperable and extensible.

To ensure full understanding and legal compliance, I strongly recommend users thoroughly review the accompanying [license agreement](https://developer.garmin.com/fit/download/). This agreement provides key information on the permissions, restrictions, and legal responsibilities related to the Garmin FIT SDK.

Source: https://developer.garmin.com/fit/overview/

The version of the Garmin FIT SDK used in this project is **21.115.00.**

## Features

- Convert Garmin FIT files into JSON format.
- Easy-to-use API for seamless integration into your Kotlin or Java applications.
- Extensible architecture to support additional output formats in the future.

## Requirements

- Java Development Kit (JDK) 19 or higher.
- Kotlin 1.8.10 or higher.
- Garmin FIT file (.fit) to convert.
