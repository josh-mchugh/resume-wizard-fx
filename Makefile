clean:
	./mvnw clean

install:
	./mvnw clean install

test:
	./mvnw clean test

run:
	./mvnw scala:run -DmainClass=net.sailware.resumewizard.Main

format:
	./mvnw spotless:apply
