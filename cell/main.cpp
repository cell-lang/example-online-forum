#include <vector>
#include <string>
#include <iostream>
#include <fstream>

#include "generated.h"


using namespace std;
using cell_lang::OnlineForum;


int main(int argc, char **argv) {
  if (argc != 4) {
    cout << "Usage: online-forum <initial state> <message list> <final state>" << endl;
    return 1;
  }

  // Reading the message list, with one message per line
  // Do not split a single message over multiple lines
  vector<string> msg_list;
  ifstream mfs(argv[2]);
  string str;
  while (getline(mfs, str)) {
    str.erase(0, str.find_first_not_of(" \t"));
    if (str != "" && str.substr(0, 2) != "//")
      msg_list.push_back(str);
  }

  // Creating an instance of OnlineForum
  OnlineForum online_forum;
  
  // Loading the initial state
  ifstream isfs(argv[1]);
  online_forum.load(isfs);

  // Printing the initial state
  online_forum.save(cout);
  cout << endl;

  // Sending the messages and printing the results
  for (int i=0 ; i < msg_list.size() ; i++) {
    string msg = msg_list[i];
    try {
      online_forum.execute(msg);
      cout << "Success: " << msg << endl;
    }
    catch (...) {
      cout << "Failure: " << msg << endl;
    }

    // // Printing the state after the update
    // cout << endl;
    // online_forum.save(cout);
    // cout << endl;
  }

  // Saving the final state to the indicated file
  ofstream osfs(argv[3]);
  online_forum.save(osfs);
  osfs << endl;
}
