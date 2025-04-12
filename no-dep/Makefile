GRAAL_21_HOME ?= /Users/heshanp/GraalVM/graalvm-community-openjdk-21.0.2+13.1/Contents/Home
GRAAL_24_HOME ?= /Users/heshanp/GraalVM/graalvm-community-openjdk-24+36.1/Contents/Home
SRC ?= main.bal
BAL ?= bal
JAVA ?= java

.PHONY: build clean bench comp analyse java_comp java_comp24

build:
	GRAALVM_HOME=$(GRAAL_21_HOME) $(BAL) build --graalvm $(SRC)
	mv main main_21
	GRAALVM_HOME=$(GRAAL_24_HOME) $(BAL) build --graalvm $(SRC)
	mv main main_24

clean:
	rm -f main_21 main_24 main.jar *.jfr

bench: build
	hyperfine "./main_21" "./main_24"

comp:
	$(BAL) build $(SRC)
	mv main.jar main.jar
	GRAALVM_HOME=$(GRAAL_21_HOME) $(BAL) build --graalvm $(SRC)
	mv main main_21
	hyperfine "java -jar main.jar" "./main_21"


comp24:
	$(BAL) build $(SRC)
	mv main.jar main.jar
	GRAALVM_HOME=$(GRAAL_24_HOME) $(BAL) build --graalvm $(SRC)
	mv main main_24
	hyperfine "java -jar main.jar" "./main_24"

analyze:
	$(BAL) build $(SRC)
	mv main.jar main.jar
	GRAALVM_HOME=$(GRAAL_21_HOME) $(BAL) build --graalvm --graalvm-build-options="--enable-monitoring=jfr" $(SRC)
	mv main main_21
	java -XX:StartFlightRecording=filename=jar_profile.jfr -jar main.jar
	./main_21 -XX:StartFlightRecording=filename=native_profile.jfr

analyze24:
	$(BAL) build $(SRC)
	mv main.jar main.jar
	GRAALVM_HOME=$(GRAAL_24_HOME) $(BAL) build --graalvm --graalvm-build-options="--enable-monitoring=jfr" $(SRC)
	mv main main_24
	java -XX:StartFlightRecording=filename=jar_profile.jfr -jar main.jar
	./main_24 -XX:StartFlightRecording=filename=native_profile.jfr

java_comp:
	$(JAVA)c Fibonacci.java
	$(GRAAL_21_HOME)/bin/native-image Fibonacci
	hyperfine "$(JAVA) Fibonacci" "./fibonacci"

java_comp24:
	$(JAVA)c Fibonacci.java
	$(GRAAL_24_HOME)/bin/native-image Fibonacci
	hyperfine "$(JAVA) Fibonacci" "./fibonacci"
