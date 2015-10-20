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
