.. _differences:

khttp's differences
===================

While khttp may be made in requests' image, it is different in many ways. This part of the documentation explores some
important ways in which it is different. It also touches on the differences in how HTTP requests are handled on a lower
level.

Notable variations from requests
--------------------------------

khttp attempts to be as close to the requests module as possible, but there are some variations, especially in features
that have yet to be implemented.

* **Some parameters are missing**

  The missing parameters from request methods are ``proxies``, ``verify``, and ``cert``. These are planned for
  implementation.

* **The json() function is split into two properties: jsonObject and jsonArray**

  There is no unifying JSON interface that both JSONObject and JSONArray implement. Rather than creating a wrapper that
  requires casting and type-checking, there are simply two properties instead of one function.

Notable variations from plain ol' Java
--------------------------------------

These are variations in how HTTP requests are handled. Obviously the methods in which the requests are made are very,
very different.

* **HTTP status codes indicative of errors don't throw exceptions**

  ``HttpURLConnection``\ s really don't like it when there are bad status codes. If you try to get the ``InputStream`` of
  any request that has an error status code, an exception will be thrown, making it so the content can't even be seen.

  khttp rectifies this. Go ahead, try ``println(get("https://httpbin.org/status/418").text)`` for a pretty picture of a
  teapot. Of course, if you were using these exceptions to check for errors, now just check the ``statusCode`` property.

* **Cookies persist across redirects**

  Java really, really doesn't handle cookies well. Cookies aren't even handled unless a global manager for all requests
  is set. That's dumb.

  khttp allows for the setting and retrieving of cookies, and they persist through redirects, if redirects are allowed.
  If a ``POST`` request is made to a login form, and a cookie is set before being redirected back to the homepage, that
  cookie will be available in the homepage response.

* **All HTTP methods are allowed, even ones that don't exist**

  Plain ol' Java limits requests to the HTTP methods that existed at the time of the creation of the HTTP classes. This
  means that some HTTP methods like PATCH aren't allowed.

  khttp overrides this, allowing any method to be used.
