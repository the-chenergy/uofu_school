/*
 * friendlist.c - [Starting code for] a web-based friend-graph manager.
 *
 * Based on:
 *  tiny.c - A simple, iterative HTTP/1.0 Web server that uses the
 *      GET method to serve static and dynamic content.
 *   Tiny Web server
 *   Dave O'Hallaron
 *   Carnegie Mellon University
 */
#include "csapp.h"
#include "dictionary.h"
#include "more_string.h"

static void doit(int fd);
static dictionary_t* read_requesthdrs(rio_t* rp);
static void read_postquery(rio_t* rp, dictionary_t* headers, dictionary_t* d);
static void clienterror(int fd, char* cause, char* errnum, char* shortmsg, char* longmsg);
static void print_stringdictionary(dictionary_t* d);
static void serve_request(int fd, dictionary_t* query);
static void serve_friends_request(int fd, dictionary_t* query);
static void serve_befriend_request(int fd, dictionary_t* query);
static void serve_unfriend_request(int fd, dictionary_t* query);
static void serve_introduce_request(int fd, dictionary_t* query);
static void send_response(int fd, char* body);

dictionary_t* friends_by_user;
pthread_mutex_t mutex;

int main(int argc, char** argv) {
  int listenfd, connfd;
  char hostname[MAXLINE], port[MAXLINE];
  socklen_t clientlen;
  struct sockaddr_storage clientaddr;

  /* Check command line args */
  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(1);
  }

  listenfd = Open_listenfd(argv[1]);

  /* Don't kill the server if there's an error, because
     we want to survive errors due to a client. But we
     do want to report errors. */
  exit_on_error(0);

  /* Also, don't stop on broken connections: */
  Signal(SIGPIPE, SIG_IGN);

  friends_by_user = make_dictionary(COMPARE_CASE_SENS, free);
  pthread_mutex_init(&mutex, NULL);

  while (1) {
    clientlen = sizeof(clientaddr);
    connfd = Accept(listenfd, (SA*)&clientaddr, &clientlen);
    if (connfd >= 0) {
      Getnameinfo((SA*)&clientaddr, clientlen, hostname, MAXLINE, port, MAXLINE, 0);
      // printf("Accepted connection from (%s, %s)\n", hostname, port);
      doit(connfd);
      Close(connfd);
    }
  }
}

/*
 * doit - handle one HTTP request/response transaction
 */
void doit(int fd) {
  char buf[MAXLINE];
  char* method;
  char* uri;
  char** uri_sections;
  char* command;
  char* version;
  rio_t rio;
  dictionary_t* headers;
  dictionary_t* query;

  /* Read request line and headers */
  Rio_readinitb(&rio, fd);
  if (Rio_readlineb(&rio, buf, MAXLINE) <= 0) return;
  // printf("%s", buf);

  if (!parse_request_line(buf, &method, &uri, &version)) {
    clienterror(fd, method, "400", "Bad Request", "Friendlist did not recognize the request");
  } else {
    if (strcasecmp(version, "HTTP/1.0") && strcasecmp(version, "HTTP/1.1")) {
      clienterror(fd, version, "501", "Not Implemented",
                  "Friendlist does not implement that version");
    } else if (strcasecmp(method, "GET") && strcasecmp(method, "POST")) {
      clienterror(fd, method, "501", "Not Implemented",
                  "Friendlist does not implement that method");
    } else {
      headers = read_requesthdrs(&rio);
      uri_sections = split_string(uri, '?');
      command = uri_sections[0];

      query = make_dictionary(COMPARE_CASE_SENS, free);
      parse_uriquery(uri, query);
      if (!strcasecmp(method, "POST")) read_postquery(&rio, headers, query);

      /* For debugging, print the dictionary */
      // printf("** uri: '%s'\n", uri);
      // printf("** query:\n");
      // print_stringdictionary(query);

      /* You'll want to handle different queries here,
         but the intial implementation always returns
         nothing: */
      if (!strcmp(command, "/friends")) {
        serve_friends_request(fd, query);
      } else if (!strcmp(command, "/befriend")) {
        serve_befriend_request(fd, query);
      } else if (!strcmp(command, "/unfriend")) {
        serve_unfriend_request(fd, query);
      } else if (!strcmp(command, "/introduce")) {
        serve_introduce_request(fd, query);
      }

      /* Clean up */
      free(uri_sections);
      free_dictionary(query);
      free_dictionary(headers);
    }

    /* Clean up status line */
    free(method);
    free(uri);
    free(version);
  }
}

/*
 * read_requesthdrs - read HTTP request headers
 */
dictionary_t* read_requesthdrs(rio_t* rp) {
  char buf[MAXLINE];
  dictionary_t* d = make_dictionary(COMPARE_CASE_INSENS, free);

  Rio_readlineb(rp, buf, MAXLINE);
  // printf("%s", buf);
  while (strcmp(buf, "\r\n")) {
    Rio_readlineb(rp, buf, MAXLINE);
    // printf("%s", buf);
    parse_header_line(buf, d);
  }

  return d;
}

void read_postquery(rio_t* rp, dictionary_t* headers, dictionary_t* dest) {
  char *len_str, *type, *buffer;
  int len;

  len_str = dictionary_get(headers, "Content-Length");
  len = (len_str ? atoi(len_str) : 0);

  type = dictionary_get(headers, "Content-Type");

  buffer = malloc(len + 1);
  Rio_readnb(rp, buffer, len);
  buffer[len] = 0;

  if (!strcasecmp(type, "application/x-www-form-urlencoded")) {
    parse_query(buffer, dest);
  }

  free(buffer);
}

static char* ok_header(size_t len, const char* content_type) {
  char *len_str, *header;

  header = append_strings("HTTP/1.0 200 OK\r\n", "Server: Friendlist Web Server\r\n",
                          "Connection: close\r\n", "Content-length: ", len_str = to_string(len),
                          "\r\n", "Content-type: ", content_type, "\r\n\r\n", NULL);
  free(len_str);

  return header;
}

/*
 * serve_request - example request handler
 */
static void serve_request(int fd, dictionary_t* query) {
  size_t len;
  char *body, *header;

  body = strdup("alice\nbob");

  len = strlen(body);

  /* Send response headers to client */
  header = ok_header(len, "text/html; charset=utf-8");
  Rio_writen(fd, header, strlen(header));
  // printf("Response headers:\n");
  // printf("%s", header);

  free(header);

  /* Send response body to client */
  Rio_writen(fd, body, len);

  free(body);
}

static void serve_friends_request(int fd, dictionary_t* query) {
  char* user;
  dictionary_t* friends_of_user;
  const char** keys;
  char* response;

  user = dictionary_get(query, "user");
  friends_of_user = dictionary_get(friends_by_user, user);
  if (friends_of_user) {
    keys = dictionary_keys(friends_of_user);
    response = join_strings(keys, '\n');
    send_response(fd, response);
    free(keys);
    free(response);
  } else {
    response = "";
    send_response(fd, response);
  }
}

static void send_response(int fd, char* body) {
  size_t len;
  char* header;

  len = strlen(body);

  header = ok_header(len, "text/html; charset=utf-8");
  Rio_writen(fd, header, strlen(header));
  free(header);

  Rio_writen(fd, body, len);
}

static void serve_befriend_request(int fd, dictionary_t* query) {
  char* user;
  dictionary_t* friends_of_user;
  char** friends_to_add;
  dictionary_t* friends_of_friend;

  user = dictionary_get(query, "user");
  friends_of_user = dictionary_get(friends_by_user, user);
  if (!friends_of_user) {
    friends_of_user = make_dictionary(COMPARE_CASE_SENS, free);
    dictionary_set(friends_by_user, user, friends_of_user);
  }

  friends_to_add = split_string(dictionary_get(query, "friends"), '\n');
  for (int i = 0; friends_to_add[i]; i++) {
    if (!strcmp(friends_to_add[i], user)) continue;  // B == A

    if (dictionary_get(friends_of_user, friends_to_add[i])) {
      continue;  // B is already A's friend
    }

    friends_of_friend = dictionary_get(friends_by_user, friends_to_add[i]);
    if (!friends_of_friend) {
      friends_of_friend = make_dictionary(COMPARE_CASE_SENS, free);
      dictionary_set(friends_by_user, friends_to_add[i], friends_of_friend);
    }

    dictionary_set(friends_of_user, friends_to_add[i], NULL);
    dictionary_set(friends_of_friend, user, NULL);
  }

  serve_friends_request(fd, query);
}

static void serve_unfriend_request(int fd, dictionary_t* query) {
  char* user;
  dictionary_t* friends_of_user;
  char** friends_to_remove;
  dictionary_t* friends_of_friend;
  char* response;

  user = dictionary_get(query, "user");
  friends_of_user = dictionary_get(friends_by_user, user);
  if (!friends_of_user) {
    response = "";
    send_response(fd, response);
    return;
  }

  friends_to_remove = split_string(dictionary_get(query, "friends"), '\n');
  for (int i = 0; friends_to_remove[i]; i++) {
    dictionary_remove(friends_of_user, friends_to_remove[i]);

    friends_of_friend = dictionary_get(friends_by_user, friends_to_remove[i]);
    if (friends_of_friend) dictionary_remove(friends_of_friend, user);
  }

  serve_friends_request(fd, query);
}

static void serve_introduce_request(int fd, dictionary_t* query) {
  char* user;
  char* friend;
  char* host;
  char* port;
  dictionary_t* friends_of_user;
  char buffer[MAXBUF];
  int dest_fd;
  rio_t rio;
  char* status;
  char* version;
  char* desc;
  dictionary_t* headers;
  int len;
  char** friends_to_add;
  dictionary_t* friends_of_friend;

  user = dictionary_get(query, "user");
  friend = dictionary_get(query, "friend");
  host = dictionary_get(query, "host");
  port = dictionary_get(query, "port");

  friends_of_user = dictionary_get(friends_by_user, user);
  if (!friends_by_user) {
    friends_by_user = make_dictionary(COMPARE_CASE_SENS, free);
    dictionary_set(friends_by_user, user, friends_of_user);
  }

  dest_fd = Open_clientfd(host, port);
  sprintf(buffer, "GET /friends?user=%s HTTP/1.1\r\n\r\n", query_encode(friend));
  Rio_writen(dest_fd, buffer, strlen(buffer));
  Shutdown(dest_fd, SHUT_WR);

  Rio_readinitb(&rio, dest_fd);
  Rio_readlineb(&rio, buffer, MAXLINE);
  parse_status_line(buffer, &version, &status, &desc);
  headers = read_requesthdrs(&rio);
  len = atoi(dictionary_get(headers, "Content-length"));
  Rio_readnb(&rio, buffer, len);
  buffer[len] = 0;

  pthread_mutex_lock(&mutex);
  {
    friends_to_add = split_string(buffer, '\n');
    for (int i = 0; friends_to_add[i]; i++) {
      if (!strcmp(friends_to_add[i], user)) continue;  // B == A

      if (dictionary_get(friends_of_user, friends_to_add[i])) {
        continue;  // B is already A's friend
      }

      friends_of_friend = dictionary_get(friends_by_user, friends_to_add[i]);
      if (!friends_of_friend) {
        friends_of_friend = make_dictionary(COMPARE_CASE_SENS, free);
        dictionary_set(friends_by_user, friends_to_add[i], friends_of_friend);
      }

      dictionary_set(friends_of_user, friends_to_add[i], NULL);
      dictionary_set(friends_of_friend, user, NULL);
    }
  }
  pthread_mutex_unlock(&mutex);

  serve_friends_request(fd, query);

  Close(dest_fd);
  free(version);
  free(status);
  free(desc);
}

/*
 * clienterror - returns an error message to the client
 */
void clienterror(int fd, char* cause, char* errnum, char* shortmsg, char* longmsg) {
  size_t len;
  char *header, *body, *len_str;

  body = append_strings("<html><title>Friendlist Error</title>",
                        "<body bgcolor="
                        "ffffff"
                        ">\r\n",
                        errnum, " ", shortmsg, "<p>", longmsg, ": ", cause,
                        "<hr><em>Friendlist Server</em>\r\n", NULL);
  len = strlen(body);

  /* Print the HTTP response */
  header = append_strings("HTTP/1.0 ", errnum, " ", shortmsg, "\r\n",
                          "Content-type: text/html; charset=utf-8\r\n",
                          "Content-length: ", len_str = to_string(len), "\r\n\r\n", NULL);
  free(len_str);

  Rio_writen(fd, header, strlen(header));
  Rio_writen(fd, body, len);

  free(header);
  free(body);
}

static void print_stringdictionary(dictionary_t* d) {
  int i, count;

  count = dictionary_count(d);
  for (i = 0; i < count; i++) {
    printf("%s=%s\n", dictionary_key(d, i), (const char*)dictionary_value(d, i));
  }
  printf("\n");
}
