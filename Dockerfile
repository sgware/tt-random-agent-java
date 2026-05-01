# Start with an official Docker OpenJDK image.
FROM eclipse-temurin

# Update packages.
RUN apt-get update

# Install GNU Screen so we can run the server and client as parallel processes.
RUN apt-get install -y screen

# Install Git so we can get the latest server and test client.
RUN apt-get install -y git

# Copy the project files into the image.
RUN mkdir /app
COPY app /app

# Set the working directory.
WORKDIR /app

# Download the latest Tandem Tales server and test client.
RUN mkdir /app/lib
RUN git clone https://github.com/sgware/tt-server.git \
   && cp tt-server/jar/tt-server.jar lib/ \
   && cp -r tt-server/worlds .
RUN git clone https://github.com/sgware/tt-test-client.git \
   && cp tt-test-client/jar/tt-test-client.jar lib/

# Clean up unused programs and files.
RUN apt-get purge -y git
RUN apt-get autoremove -y
RUN rm -rf /var/lib/apt/lists/*
RUN rm -rf tt-server
RUN rm -rf tt-test-client

# Make shell scripts executable commands.
RUN chmod +x compile
RUN mv compile /usr/local/bin
RUN chmod +x test
RUN mv test /usr/local/bin
RUN chmod +x run
RUN mv run /usr/local/bin

# Compile the client source.
RUN ["compile"]

# By default, starting a container runs the client.
CMD ["run"]