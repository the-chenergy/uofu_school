#include "string_set.h"

#include <iostream>
#include <sstream>
#include <vector>

using namespace cs3505;
using namespace std;

int main()
{
  string_set* s = new string_set(1);

  s->add("emma");
  s->add("bye");
  s->add("diana");
  s->add("emma");
  s->add("fernanda");
  s->add("breanna");
  s->add("wow");
  s->add("ariana");
  s->add("claudia");
  s->add("claudia");
  s->add("ariana");

  s->remove("bye");
  s->remove("wow");

  string_set t(6);
  t.add("hooray");
  t = *s;

  cout << t.get_size() << endl;

  vector<string> v = t.get_elements();
  for (string e : v)
    cout << e << endl;

  cout << t.contains("ariana");
  cout << t.contains("breanna");
  cout << t.contains("claudia");
  cout << t.contains("diana");
  cout << t.contains("emma");
  cout << t.contains("fernanda");

  cout << t.contains("bye");
  cout << t.contains("wow");

  cout << endl;

  delete s;

  cout << t.get_size() << endl;

  string_set u(18);
  for (int i = 0; i < 248832; i++)
  {
    ostringstream s;
    s << "item" << i;

    u.add(s.str());
  }
  cout << u.get_size() << endl;
  cout << u.get_elements()[0] << endl;
  cout << u.get_elements()[248831] << endl;

  return 0;
}
