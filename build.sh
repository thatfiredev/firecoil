#!/bin/bash
# Build script based on: https://github.com/firebase/quickstart-android/blob/master/build.sh

# Exit on error
set -e

# Work off travis
if [[ ! -z TRAVIS_PULL_REQUEST ]]; then
  echo "TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
else
  echo "TRAVIS_PULL_REQUEST: unset, setting to false"
  TRAVIS_PULL_REQUEST=false
fi

# Build
if [ $TRAVIS_PULL_REQUEST = false ] ; then
  echo "Building and testing full project"
  # For a merged commit, build and test all configurations.
  echo "${GOOGLE_SERVICES}" | base64 --decode > sampleapp/google-services.json
  ./gradlew clean test
else
  # On a pull request, just build debug which is much faster and catches
  # obvious errors.
  # Copy mock google-services file
  echo "Using mock google-services.json"
  cp mock-google-services.json sampleapp/google-services.json
  ./gradlew clean testDebugUnitTest check
fi