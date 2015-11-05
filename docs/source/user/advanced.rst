.. _advanced:

Advanced usage
==============

This document covers some of khttp's more advanced features.

Streaming uploads
-----------------

khttp supports streaming uploads, which allow you to send large streams or files without reading them into memory. To
stream and upload, simply provide a ``File`` object for your body:

::

    val file = File("massive-body")
    post("http://some.url/streamed", data = file)
