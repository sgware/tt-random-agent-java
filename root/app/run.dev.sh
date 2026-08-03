#!/bin/sh

# Import the server's self-signed public key into a trust store.
if [ ! -f /etc/tt/certs/tt-truststore.p12 ]; then
    echo "Importing server's self-signed public key into a Java-compatible trust store..."
    keytool \
      -importcert \
      -file /etc/tt/certs/tt-fullchain.pem \
      -keystore /etc/tt/certs/tt-truststore.p12 \
      -storetype PKCS12 \
      -storepass "changeit" \
      -alias "tt" \
	  -noprompt \
	  > /dev/null 2>&1
fi

# Run the compiled Java client with the self-signed trust store.
java \
  -Djavax.net.ssl.trustStore="/etc/tt/certs/tt-truststore.p12" \
  -Djavax.net.ssl.trustStorePassword="changeit" \
  --enable-final-field-mutation=ALL-UNNAMED \
  -cp "/opt/tt/*:/app/bin" \
  tt.RandomAgentMain
