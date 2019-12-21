online-forum.jar: cell/online-forum.cell cell/main.java
	rm -rf tmp/
	mkdir tmp/ tmp/gen/ tmp/cls/
	java -jar bin/cellc-java.jar -d project.txt tmp/gen/
	javac -d tmp/cls/ cell/main.java tmp/gen/*.java
	jar cfe online-forum.jar Main -C tmp/cls/ .

online-forum.dll: cell/online-forum.cell cell/main.cs
	rm -rf tmp/
	mkdir tmp/
	dotnet bin/cellc-cs.dll -d project.txt tmp/
	cp cell/main.cs dotnet/
	mv tmp/generated.cs tmp/runtime.cs tmp/facades.cs dotnet/
	dotnet build -c Release dotnet/

clean:
	rm -rf tmp/ online-forum.jar debug/*
	rm -rf dotnet/*.cs dotnet/bin/ dotnet/obj/
