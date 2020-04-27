java: cell/online-forum.cell cell/main.java
	rm -rf tmp/
	mkdir tmp/ tmp/gen/ tmp/cls/
	java -jar bin/cellc-java.jar -d project.txt tmp/gen/
	javac -d tmp/cls/ cell/main.java tmp/gen/*.java
	jar cfe tmp/online-forum.jar Main -C tmp/cls/ .

csharp: cell/online-forum.cell cell/main.cs
	rm -rf tmp/
	mkdir tmp/ tmp/dotnet/
	dotnet bin/cellc-cs.dll -d project.txt tmp/
	cp cell/main.cs online-forum.csproj tmp/dotnet/
	mv tmp/generated.cs tmp/runtime.cs tmp/automata.cs tmp/typedefs.cs tmp/dotnet/
	dotnet build -c Release tmp/dotnet/

clean:
	rm -rf tmp/ debug/*
