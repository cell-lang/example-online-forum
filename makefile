online-forum.jar: cell/online-forum.cell cell/main.java
	rm -rf tmp/
	mkdir tmp/
	mkdir tmp/gen/ tmp/cls/
	java -jar bin/cellc-java.jar -d project.txt tmp/gen/
	javac -d tmp/cls/ cell/main.java tmp/gen/*.java
	jar cfe online-forum.jar Main -C tmp/cls/ .

clean:
	rm -rf tmp/ online-forum.jar
