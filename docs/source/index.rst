.. khttp documentation master file, created by
   sphinx-quickstart on Tue Oct 20 13:22:24 2015.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

khttp: HTTP without the bullshit
================================

khttp is a `Mozilla Public License 2.0 licensed <https://github.com/jkcclemens/khttp/blob/master/LICENSE>`_ library,
written in Kotlin, inspired by requests, for human beings.

Java is **bad** at HTTP. Really bad. The tools provided are functionally broken. Unfortunately, by extension, Kotlin is
also bad at HTTP. Yet, more and more, the need to interact with APIs and perform basic HTTP requests shows up in
programs, but the broken tools in Java make it a big fuss. This isn't how it should be.

To combat this, libraries like `Unirest <http://unirest.io/java.html>`_ have popped up, but they introduce massive
amounts of overhead to just make a simple GET request.

khttp attempts to solve this problem, by mimicking the Python `requests <http://docs.python-requests.org/en/latest/>`_
module. khttp uses only the ``org.json.json`` library and native Java. There's nothing else. No bullshit.

::

    val r = get("https://api.github.com/user", auth=BasicAuthorization("user", "pass"))
    r.statusCode
    // 200
    r.headers["Content-Type"]
    // "application/json; charset=utf-8"
    r.text
    // """{"type": "User"..."""
    r.jsonObject
    // org.json.JSONObject

Feature support
---------------

khttp is ready for today's web.

* International domains and URLs
* Sessions with cookie persistence
* Basic authentication
* Elegant key/value cookies
* Automatic decompression
* Unicode response bodies
* Connection timeouts

User guide
----------

.. toctree::
   :maxdepth: 2

   user/install
   user/differences
   user/quickstart
