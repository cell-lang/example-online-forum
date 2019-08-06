import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;

import net.cell_lang.OnlineForum;


class Main {
  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      System.err.println("Usage: java -jar online-forum.jar <initial state> <message list> <final state>");
      return;
    }

    // Reading the message list, with one message per line
    // Do not split a single message over multiple lines
    List<String> msgList = Files.readAllLines(Paths.get(args[1]), Charset.defaultCharset());

    // Creating an instance of OnlineForum
    OnlineForum onlineForum = new OnlineForum();

    // Loading the initial state
    try (Reader reader = new FileReader(args[0])) {
      onlineForum.load(reader);
    }

    // Printing the initial state
    onlineForum.save(new PrintWriter(System.out));
    System.out.println();

    // Sending the messages and printing the results
    for (String line : msgList) {
      String msg = line.trim();
      // Skipping lines that are empty or commented out
      if (!msg.equals("") && !msg.startsWith("//")) {
        try {
          onlineForum.execute(msg);
          System.out.printf("Success: %s\n", msg);
        }
        catch (RuntimeException e) {
          System.out.printf("Failure: %s\n", msg);
        }
        // // Printing the state after the update
        // System.out.println();
        // onlineForum.save(new PrintWriter(System.out));
        // System.out.println();
      }
    }

    // Saving the final state to the indicated file
    try (Writer writer = new FileWriter(args[2])) {
      onlineForum.save(writer);
      writer.write("\n");
    }
  }
}
