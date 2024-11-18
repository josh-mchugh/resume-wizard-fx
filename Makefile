clean:
	./mvnw clean

install:
	./mvnw clean install

run:
	./mvnw scala:run -DmainClass=net.sailware.resumewizard.Main

format:
	./mvnw spotless:apply
