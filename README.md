# Online forum example

Code for the online forum example described <a href="http://cell-lang.net/example.html">here</a> and <a href="http://www.cell-lang.net/comparison.html">here</a>.

The Cell code is contained in <a href="cell/online-forum.cell">```online-forum.cell```</a>, and the use of generated Java class is demonstrated in <a href="cell/main.java">```main.java```</a> To build the Cell version of the application just type ```make```. To run it type
```
  java -jar online-forum.jar sample-state.txt msg-list.txt output-state.txt
```
The input file <a href="sample-state.txt">```sample-state.txt```</a> contains the initial state for the automaton, <a href="msg-list.txt">```msg-list.txt```</a> the sequence of messages that must be sent to it, and ```output-state.txt``` will contain the updated state.

The Java equivalent of the code in ```online-forum.cell``` is contained in <a href="java/OnlineForum.java">```OnlineForum.java```</a>.
