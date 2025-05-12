# Use an OpenJDK image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy all files from the current directory to the container
COPY . .

#RUN apk add --no-cache findutils
#CMD javac -d out $(find . -name "*.java")
RUN chmod 777 ../app

# Build and run the application
CMD ["java -cp out SortBenchMark"]
