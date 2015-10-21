.. _quickstart:

Quickstart
==========

Ready to get started with a better way to make requests? This part of the documentation aims to get you up and running
in no time.

Let's start with some simple examples, analogous
`to the ones found in requests' quickstart guide <http://docs.python-requests.org/en/latest/user/quickstart/>`_.

Make a request
--------------

Making a request with khttp is very simple.

Start by importing the request method for the type of request you want to make. For this example, we'll be using GET, so
let's import that.

::

    import me.kyleclemens.khttp.get

Now, let's try to get a webpage. For example, let's get GitHub's public timeline.

::

    val r = get("https://api.github.com/events")

Now, we have a KHttpResponse object called ``r``. We can get all the information we need from this object.

khttp's API is straightforward, so all forms of HTTP requests are pretty obvious. For example, here's a quick POST
request.

::

    import me.kyleclemens.khttp.post
    post("http://httpbin.org/post", data = mapOf("key" to "value"))

Easy. khttp supports every other HTTP request, as well.

::

    import me.kyleclemens.khttp.*
    var r = put("http://httpbin.org/put", data = mapOf("key" to "value"))
    r = delete("http://httpbin.org/delete")
    r = head("http://httpbin.org/get")
    r = options("http://httpbin.org/get")

If, for some reason, you need a nonstandard request type, that's supported too.

::

    import me.kyleclemens.khttp.request
    val r = request("NONSTANDARD", "http://example.com")

*Note:* From here on out, the import statements are going to be dropped from the code snippets. They should all be easy
enough to figure out.

Passing parameters in URLs
--------------------------

You often want to send some sort of data in the URL's query string. If you were constructing the URL by hand, this data
would be given as key/value pairs in the URL after a question mark, e.g. ``httpbin.org/get?key=val``. khttp allows you
to provide these arguments as an object, using the params argument. As an example, if you wanted to pass ``key1=value1``
and ``key2=value2`` to ``httpbin.org/get``\ , you would use the following code:

::

    val payload = mapOf("key1" to "value1", "key2" to "value2")
    val r = get("http://httpbin.org/get", params=payload)

Note that there is a ``Parameters`` class that can be used, as well. There's no real advantage to using it, but it is an
option. Note that ``FormParameters`` is used for data, not URL parameters (although it will still work as URL parameters
if given to the params argument).

You can see that the URL has been correctly encoded by printing the URL.

*Note:* At the moment, only ``String``\ s can be passed as parameters. In the future, ``Any`` will be allowed, which
will enable ``Collection``\ s to be passed.

::

    println(r.url)
    // http://httpbin.org/get?key1=value1&key2=value2

Response content
----------------

We can read the content of the server’s response. Consider the GitHub timeline again:

::

    val r = get("https://api.github.com/events")
    println(r.text)
    // [{"repository":{"open_issues":0,"url":"https://github.com/...

When you make a request, khttp makes educated guesses about the encoding of the response based on the HTTP headers. The
text encoding guessed by khttp is used when you access ``r.text``\ . You can find out what encoding khttp is using, and
change it, using the ``r.encoding`` property:

::

    println(r.encoding)
    // UTF-8
    r.encoding = Charsets.ISO_8859_1

If you change the encoding, khttp will use the new value of ``r.encoding`` whenever you call ``r.text``\ . You might
want to do this in any situation where you can apply special logic to work out what the encoding of the content will be.
For example, HTTP and XML have the ability to specify their encoding in their body. In situations like this, you should
use ``r.raw`` to find the encoding, and then set ``r.encoding``\ . This will let you use ``r.text`` with the correct
encoding.

Binary response content
-----------------------

You can also access the response body as a ``ByteArray``\ , for non-text requests:

::

    r.content
    // ByteArray

The ``gzip`` and ``deflate`` transfer-encodings are automatically decoded for you.

JSON response content
---------------------

In case you're dealing with JSON data, khttp will use ``org.json.json`` to provide two properties: jsonObject and
jsonArray.

::

    val r = get("https://api.github.com/events")
    println(r.jsonArray)
    // [{"actor":{"avatar_url":"https://avatars.githubusercontent.com/u/...

Note that if you attempt to access jsonObject but the content is an array, an exception will be thrown and vice versa.
If the content is not JSON, an exception will also be thrown.

It should be noted that the success of calls to these properties does **not** indicate the success of the response. Some
servers may return a JSON object in a failed response (e.g. error details with HTTP 500). Such JSON will be decoded and
returned. To check that a request is successful, check that ``r.statusCode`` is what you expect.


Raw response content
--------------------

In the rare case that you’d like to get the raw InputStream response from the server, you can access ``r.raw``\ .

::

    val r = get("https://api.github.com/events")
    r.raw
    // InputStream

Custom headers
--------------

If you’d like to add HTTP headers to a request, simply pass in a Map to the headers parameter.

For example, accessing a super secret API:

::

    val r = get("https://my.api/some/endpoint", headers=mapOf("X-API-Key" to "secret"))

Some headers may be overwritten depending on context. For example, if the json argument is specified, the Content-Type
header will be forced to ``application/json``.

khttp does not change behavior based on any specified request headers. It **does** change behavior based on response
headers.

More complicated POST requests
------------------------------

Typically, you want to send some form-encoded data — much like an HTML form. To do this, simply pass a Map to the data
argument. Your Map of data will automatically be form-encoded when the request is made:

::

    val payload = mapOf("key1" to "value1", "key2" to "value2")
    val r = post("http://httpbin.org/post", data=payload)
    println(r.text)
    /*
    {
      ...
      "form": {
        "key1": "value1",
        "key2": "value2"
      },
      ...
    }
    */

There are many times that you want to send data that is not form-encoded. If you pass in any object except for a Map,
that data will be posted directly (via the ``toString()`` method).

For example, the GitHub API v3 accepts JSON-Encoded POST/PATCH data:

::

    val url = "https://api.github.com/some/endpoint"
    val payload = mapOf("some" to "data")

    val r = post(url, data=JSONObject(payload))

Instead of encoding the JSON yourself, you can also pass it directly using the ``json`` parameter, and it will be
encoded automatically:

::

    val url = "https://api.github.com/some/endpoint"
    val payload = mapOf("some" to "data")

    r = post(url, json=payload)

Response status codes
---------------------

We can check the response status code:

::

    val r = get("http://httpbin.org/get")
    r.statusCode
    // 200

Response headers
----------------

We can view the server's response headers using a Map:

::

    r.headers
    // {Server=nginx, Access-Control-Allow-Origin=*, Access-Control-Allow-Credentials=true, Connection=keep-alive, Content-Length=235, Date=Wed, 21 Oct 2015 17:19:06 GMT, Content-Type=application/json}

The Map is special, though: it's made just for HTTP headers. According to
`RFC 7230 <http://tools.ietf.org/html/rfc7230#section-3.2>`_, HTTP header names are case-insensitive.

So, we can access the headers using any capitalization we want:

::

    headers["Content-Type"]
    // application/json
    r.headers.get("content-type")
    // application/json

It is also special in that the server could have sent the same header multiple times with different values, but khttp
combines them so they can be represented in the Map within a single mapping, as per
`RFC 7230 <http://tools.ietf.org/html/rfc7230#section-3.2>`_:

    A recipient MAY combine multiple header fields with the same field name into one "field-name: field-value" pair,
    without changing the semantics of the message, by appending each subsequent field value to the combined field value
    in order, separated by a comma.
