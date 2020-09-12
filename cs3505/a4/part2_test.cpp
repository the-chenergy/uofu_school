/*
 * Assignment 04 -- An exploration of polymorphism in multiple
 * inheritance.  This code is instrumented to allow the user to see
 * function calls as they happen.
 *
 * Students need to supply the missing class declarations, constructors,
 * and destructors, as well as any missing or otherwise required functions.
 * When you're done, the output should match the sample output --exactly--.
 *
 * Find the TODO: comments, and replace them with the relevant code.
 *
 * Note that while this code is motivated by a particular problem,
 * much of the functionality is missing.  Students should not try to
 * implement observer functionality or remote connections.
 *
 * Additionally, while you can make multiple inheritance compile, its much
 * tougher to make it work properly.  Students should not fix the
 * deficient design here.  Get the code to compile and match the given
 * design.  Later, you will describe possible fixes in essay questions.
 *
 * Peter Jensen and Qianlang Chen
 * February 28, 2020
 */

#include <iostream>
#include <string>
#include <vector>

using namespace std;

class observer; // Forward declare this class, we won't make/use it.

/*******************
 * My status class *
 *******************/

class status
{
public:
  status(string s);
  virtual ~status();

  virtual void set(string s);
  virtual string get();

protected:
  string s;
};

status::status(string s) : s(s)
{
  cout << "      ==> status::status" << endl;
  cout << "      <-- status::status" << endl;
}

status::~status()
{
  cout << "      ==> status::~status" << endl;
  cout << "      <-- status::~status" << endl;
}

void status::set(string s)
{
  cout << "      ==> status::set" << endl;
  this->s = s;
  cout << "      <-- status::set" << endl;
}

string status::get()
{
  cout << "      ==> status::get" << endl;
  cout << "      <-- status::get" << endl;
  return s;
}

/****************************
 * My remote status class *
 ****************************/

class remote : public virtual status
{
public:
  remote(string s);
  virtual ~remote();
  void set(string st);
  string get();

protected:
  bool has_remote_changed();
  void set_remote_status(string status);
  string get_remote_status();
};

remote::remote(string s) : status(s)
{
  cout << "    ==> remote::remote" << endl;
  cout << "    <-- remote::remote" << endl;
}

remote::~remote()
{
  cout << "    ==> remote::~remote" << endl;
  cout << "    <-- remote::~remote" << endl;
}

void remote::set(string st)
{
  cout << "    ==> remote::set" << endl;

  // Update the remote status and also keep it (as a status).

  this->set_remote_status(st);
  status::set(st); // Change superclass field

  cout << "    <-- remote::set" << endl;
}

string remote::get()
{
  cout << "    ==> remote::get" << endl;

  // If the remote status has changed, we'll update this
  //   object to its remote status before returning it.

  // To avoid resetting the remote status, only set the status
  //   in the superclass.  (Don't use the 'set' function in
  //   this class, or it will set the remote status to what we
  //   just got remotely, which is a waste of time.)

  if (has_remote_changed())
    status::set(this->get_remote_status());

  // Just get the status (from the superclass) and return it.

  string result = status::get();

  cout << "    <-- remote::get" << endl;

  return result;
}

bool remote::has_remote_changed()
{
  cout << "    ==> remote::has_remote_changed" << endl;
  /* Assume there would be code here to check the remote value. */
  cout << "    <-- remote::has_remote_changed" << endl;

  // This function might return true or false at any time (based
  //   on some remote status).  For this test, I'll just return
  //   false.  (You must assume that true might be returned in
  //   the actual, finished program.)

  bool debug_return_value = true;
  return debug_return_value;
}

string remote::get_remote_status()
{
  cout << "    ==> remote::get_remote_status" << endl;
  /* Assume a remote status is retrieved. */
  cout << "    <-- remote::get_remote_status" << endl;

  return "done"; // Just a stub -- any status might be returned.
}

void remote::set_remote_status(string status)
{
  cout << "    ==> remote::set_remote_status" << endl;
  // Assume the given status is remotely stored/updated. */
  cout << "    <-- remote::set_remote_status" << endl;
}

/***********************
 * My observable class *
 ***********************/

class observable : public virtual status
{
public:
  observable(string s);
  virtual ~observable();

  void register_observer(observer* o);
  void set(string new_status);

protected:
  void notify_observers();
};

observable::observable(string s) : status(s)
{
  cout << "    ==> observable::observable" << endl;
  cout << "    <-- observable::observable" << endl;
}

observable::~observable()
{
  cout << "    ==> observable::~observable" << endl;
  cout << "    <-- observable::~observable" << endl;
}

void observable::set(string new_status)
{
  cout << "    ==> observable::set" << endl;

  // Only change the status and send out notifications
  //   if the new status is different than the old one.

  if (new_status != get())
  {
    status::set(new_status);
    notify_observers();
  }

  cout << "    <-- observable::set" << endl;
}

void observable::notify_observers()
{
  cout << "    ==> observable::notify_observers" << endl;
  /* Assume observer/delegates are activated. */
  cout << "    <-- observable::notify_observers" << endl;
}

void observable::register_observer(observer* o)
{
  cout << "    ==> observable::register_observer" << endl;
  /* Assume an observer is added to our list of observers. */
  cout << "    <-- observable::register_observer" << endl;
}

/************************
 * My user_status class *
 ************************/

class user_status : public observable, public remote
{
public:
  user_status(string s);
  virtual ~user_status();

  void set(string s);
};

user_status::user_status(string s) : observable(s), remote(s), status(s)
{
  cout << "  ==> user_status::user_status" << endl;
  cout << "  <-- user_status::user_status" << endl;
}

user_status::~user_status()
{
  cout << "  ==> user_status::~user_status" << endl;
  cout << "  <-- user_status::~user_status" << endl;
}

void user_status::set(string s)
{
  cout << "  ==> user_status::set" << endl;

  observable::set(s);
  remote::set(s);

  cout << "  <-- user_status::set" << endl;
}

/********************
 * My main (tester) *
 ********************/

int main()
{
  switch (2)
  {
    case 1:
    {
      user_status* us = new user_status("online");
      observable* ob = us;

      cout << "###### test starts ######" << endl;

      ob->set("offline");

      break;
    }
    case 2:
    {
      user_status* us = new user_status("online");

      cout << "###### test starts ######" << endl;

      us->get();

      break;
    }
  }

  return 0;
}
