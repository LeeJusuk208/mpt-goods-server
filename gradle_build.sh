# make gradle zip files
zip -s 0 ./gradle/wrapper/gradle-8.1.1.zip --out ./gradle/wrapper/gradle-8.1.1-bin.zip

# build stage
./gradlew clean build

# clear temp files 
rm ./gradle/wrapper/gradle-8.1.1-bin.zip