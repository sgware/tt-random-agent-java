# Start with Linux that has the JDK installed.
FROM eclipse-temurin

# Update packages.
RUN apt-get update

# Install GNU Screen so we can run the server and client as parallel processes.
RUN apt-get install -y screen

# Install Git so we can get the latest server and test client.
RUN apt-get install -y git

# Download the latest Tandem Tales server and test client.
RUN mkdir /opt/tt
RUN git clone https://github.com/sgware/tt-server.git \
   && cp tt-server/jar/tt-server.jar /opt/tt \
   && cp -r tt-server/worlds /opt/tt
RUN git clone https://github.com/sgware/tt-test-client.git \
   && cp tt-test-client/jar/tt-test-client.jar /opt/tt

# Clean up unused programs and files.
RUN apt-get purge -y git
RUN apt-get autoremove -y
RUN rm -rf /var/lib/apt/lists/*
RUN rm -rf tt-server
RUN rm -rf tt-test-client
RUN rm -rf /tmp/*

# Copy shell scripts and make them executable.
COPY usr/local/bin usr/local/bin
RUN chmod +x /usr/local/bin/compile
RUN chmod +x /usr/local/bin/start_test_server
RUN chmod +x /usr/local/bin/start_client
RUN chmod +x /usr/local/bin/start_test_client
RUN chmod +x /usr/local/bin/test
RUN chmod +x /usr/local/bin/run

# Copy the project files into the image.
COPY app /app
WORKDIR /app

# Compile the client source.
RUN ["compile"]

# By default, starting a container runs the client.
CMD ["run"]